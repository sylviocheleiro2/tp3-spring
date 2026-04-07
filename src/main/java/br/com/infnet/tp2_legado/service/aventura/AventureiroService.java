package br.com.infnet.tp2_legado.service.aventura;

import br.com.infnet.tp2_legado.dto.aventura.*;
import br.com.infnet.tp2_legado.enums.ClasseAventureiro;
import br.com.infnet.tp2_legado.enums.StatusMissao;
import br.com.infnet.tp2_legado.model.audit.Organizacao;
import br.com.infnet.tp2_legado.model.audit.Usuario;
import br.com.infnet.tp2_legado.model.aventura.Aventureiro;

import br.com.infnet.tp2_legado.model.aventura.Companheiro;
import br.com.infnet.tp2_legado.model.aventura.ParticipacaoMissao;
import br.com.infnet.tp2_legado.repository.audit.OrganizacaoRepository;
import br.com.infnet.tp2_legado.repository.audit.UsuarioRepository;
import br.com.infnet.tp2_legado.repository.aventura.AventureiroRepository;

import br.com.infnet.tp2_legado.dto.audit.OrganizacaoResponse;

import br.com.infnet.tp2_legado.repository.aventura.CompanheiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AventureiroService {

    private final CompanheiroRepository companheiroRepository;
    private final AventureiroRepository aventureiroRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public AventureiroResponse cadastrar(AventureiroRequest request)
    {

        Organizacao org = organizacaoRepository.findById(request.organizacaoId())
                .orElseThrow(() -> new RuntimeException("ERRO: Organização não encontrada."));


        Usuario responsavel = usuarioRepository.findById(request.usuarioResponsavelId())
                .orElseThrow(() -> new RuntimeException("ERRO: Usuário responsável não encontrado."));

        // REGRA: Restrição de Relacionamento Cruzado
        // O usuário que cadastra DEVE pertencer à mesma organização do aventureiro.
        if (!responsavel.getOrganizacao().getId().equals(org.getId())) {
            throw new RuntimeException("VIOLAÇÃO: O usuário responsável não pertence à organização do aventureiro.");
        }

        Aventureiro aventureiro = Aventureiro.builder()
                .nome(request.nome())
                .classe(request.classe())
                .nivel(request.nivel() != null && request.nivel() >= 1 ? request.nivel() : 1) // Min 1
                .ativo(true) // Novo aventureiro sempre ativo
                .organizacao(org)
                .usuarioResponsavel(responsavel)
                .build();

        Aventureiro salvo = aventureiroRepository.save(aventureiro);

        return new AventureiroResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getClasse(),
                salvo.getNivel(),
                salvo.getAtivo(),
                new OrganizacaoResponse(
                        salvo.getOrganizacao().getId(),
                        salvo.getOrganizacao().getNome()
                )
        );
    }

    @Transactional
    public void deletar(Long id)
    {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado para exclusão."));

        aventureiroRepository.delete(aventureiro);
    }

    @Transactional
    public CompanheiroResponse adotarCompanheiro(Long aventureiroId, CompanheiroRequest request)
    {

        Aventureiro aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado."));


        if (aventureiro.getCompanheiro() != null) {
            throw new RuntimeException("Este aventureiro já possui um fiel companheiro!");
        }

        Companheiro companheiro = Companheiro.builder()
                .nome(request.nome())
                .especie(request.especie())
                .lealdade(request.lealdade() != null ? request.lealdade() : 0)
                .aventureiro(aventureiro)
                .build();

        Companheiro salvo = companheiroRepository.save(companheiro);

        return new CompanheiroResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEspecie(),
                salvo.getLealdade()
        );
    }

    public Page<AventureiroResponse> listarComFiltros(
            Long organizacaoId,
            Boolean ativo,
            ClasseAventureiro classe,
            Integer nivelMinimo,
            Pageable pageable)
    {

        // Criamos a consulta dinâmica (Specification)
        Specification<Aventureiro> spec = Specification.where((root, query, cb) ->
                cb.equal(root.get("organizacao").get("id"), organizacaoId) // REGRA: Sempre isolar por Org
        );

        if (ativo != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("ativo"), ativo));
        }

        if (classe != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("classe"), classe));
        }

        if (nivelMinimo != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("nivel"), nivelMinimo));
        }

        // Busca paginada
        return aventureiroRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    private AventureiroResponse mapToResponse(Aventureiro a)
    {
        return new AventureiroResponse(
                a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo(),
                new OrganizacaoResponse(a.getOrganizacao().getId(), a.getOrganizacao().getNome())
        );
    }

    @Transactional(readOnly = true)
    public Page<AventureiroResponse> buscarPorNome(Long organizacaoId, String nome, Pageable pageable)
    {
        // Começamos com a trava de segurança da Organização
        Specification<Aventureiro> spec = Specification.where((root, query, cb) ->
                cb.equal(root.get("organizacao").get("id"), organizacaoId)
        );

        // Adicionamos a busca parcial se o nome não estiver vazio
        if (nome != null && !nome.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%")
            );
        }

        return aventureiroRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public AventureiroDetalheResponse obterDetalhes(Long id)
    {
        Aventureiro a = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado."));

        CompanheiroResponse companheiroDto = null;
        if (a.getCompanheiro() != null) {
            companheiroDto = new CompanheiroResponse(
                    a.getCompanheiro().getId(),
                    a.getCompanheiro().getNome(),
                    a.getCompanheiro().getEspecie(),
                    a.getCompanheiro().getLealdade()
            );
        }

        long totalParticipacoes = a.getParticipacoes().size();

        String ultimaMissao = a.getParticipacoes().stream()
                .max(Comparator.comparing(ParticipacaoMissao::getDataRegistro))
                .map(p -> p.getMissao().getTitulo())
                .orElse("Nenhuma missão registrada");

        return new AventureiroDetalheResponse(
                a.getId(),
                a.getNome(),
                a.getClasse(),
                a.getNivel(),
                a.getAtivo(),
                new OrganizacaoResponse(a.getOrganizacao().getId(), a.getOrganizacao().getNome()),
                companheiroDto,
                totalParticipacoes,
                ultimaMissao
        );
    }

    @Transactional(readOnly = true)
    public List<RankingAventureiroResponse> gerarRanking(
            Long organizacaoId,
            StatusMissao status,
            LocalDateTime inicio,
            LocalDateTime fim) {

        return aventureiroRepository.obterRanking(organizacaoId, status, inicio, fim);
    }
}