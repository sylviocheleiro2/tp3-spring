package br.com.infnet.tp3_spring.service.aventura;

import br.com.infnet.tp3_spring.dto.aventura.*;
import br.com.infnet.tp3_spring.enums.ClasseAventureiro;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import br.com.infnet.tp3_spring.exceptions.BusinessRuleException;
import br.com.infnet.tp3_spring.exceptions.ResourceNotFoundException;
import br.com.infnet.tp3_spring.model.audit.Organizacao;
import br.com.infnet.tp3_spring.model.audit.Usuario;
import br.com.infnet.tp3_spring.model.aventura.Aventureiro;

import br.com.infnet.tp3_spring.model.aventura.Companheiro;
import br.com.infnet.tp3_spring.model.aventura.ParticipacaoMissao;
import br.com.infnet.tp3_spring.repository.audit.OrganizacaoRepository;
import br.com.infnet.tp3_spring.repository.audit.UsuarioRepository;
import br.com.infnet.tp3_spring.repository.aventura.AventureiroRepository;

import br.com.infnet.tp3_spring.dto.audit.OrganizacaoResponse;

import br.com.infnet.tp3_spring.repository.aventura.CompanheiroRepository;
import br.com.infnet.tp3_spring.repository.aventura.specs.AventureiroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new ResourceNotFoundException("ERRO: Organização não encontrada."));


        Usuario responsavel = usuarioRepository.findById(request.usuarioResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("ERRO: Usuário responsável não encontrado."));

        // REGRA: Restrição de Relacionamento Cruzado
        // O usuário que cadastra DEVE pertencer à mesma organização do aventureiro.
        if (!responsavel.getOrganizacao().getId().equals(org.getId())) {
            throw new BusinessRuleException("VIOLAÇÃO: O usuário responsável não pertence à organização do aventureiro.");
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
                .orElseThrow(() -> new ResourceNotFoundException("Aventureiro não encontrado para exclusão."));

        aventureiroRepository.delete(aventureiro);
    }

    @Transactional
    public CompanheiroResponse adotarCompanheiro(Long aventureiroId, CompanheiroRequest request)
    {

        Aventureiro aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new ResourceNotFoundException("Aventureiro não encontrado."));


        if (aventureiro.getCompanheiro() != null) {
            throw new BusinessRuleException("Este aventureiro já possui um fiel companheiro!");
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



    public Page<AventureiroResponse> listarComFiltros(AventureiroFiltroRequest filtro, Pageable pageable)
    {
        return aventureiroRepository.findAll(
                AventureiroSpecs.comFiltros(filtro.organizacaoId(), filtro.ativo(), filtro.classe(), filtro.nivelMinimo()),
                pageable
        ).map(this::mapToResponse);
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
        return aventureiroRepository.findAll(
                AventureiroSpecs.porNome(organizacaoId, nome),
                pageable
        ).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public AventureiroDetalheResponse obterDetalhes(Long id)
    {
        Aventureiro a = aventureiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aventureiro não encontrado."));

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