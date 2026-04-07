package br.com.infnet.tp3_spring.service.aventura;

import br.com.infnet.tp3_spring.dto.aventura.*;
import br.com.infnet.tp3_spring.model.audit.Organizacao;
import br.com.infnet.tp3_spring.model.aventura.Missao;

import br.com.infnet.tp3_spring.repository.audit.OrganizacaoRepository;
import br.com.infnet.tp3_spring.repository.aventura.MissaoRepository;

import br.com.infnet.tp3_spring.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import br.com.infnet.tp3_spring.enums.NivelPerigo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissaoService {

    private final MissaoRepository missaoRepository;
    private final OrganizacaoRepository organizacaoRepository;

    @Transactional
    public MissaoResponse criar(MissaoRequest request)
    {

        Organizacao org = organizacaoRepository.findById(request.organizacaoId())
                .orElseThrow(() -> new RuntimeException("Organização não encontrada para criar a missão."));

        if (!org.getAtivo()) {
            throw new RuntimeException("Não é possível criar missões para uma organização inativa.");
        }


        Missao missao = Missao.builder()
                .titulo(request.titulo())
                .nivelPerigo(request.nivelPerigo())
                .status(StatusMissao.PLANEJADA) // Toda missão nasce como Planejada
                .dataInicio(request.dataInicio())
                .organizacao(org)
                .build();

        Missao salva = missaoRepository.save(missao);

        return new MissaoResponse(
                salva.getId(),
                salva.getTitulo(),
                salva.getNivelPerigo(),
                salva.getStatus(),
                salva.getDataInicio(),
                salva.getDataTermino(),
                new OrganizacaoResponse(org.getId(), org.getNome())
        );
    }

    @Transactional(readOnly = true)
    public Page<MissaoResponse> listarComFiltros(
            Long organizacaoId,
            StatusMissao status,
            NivelPerigo nivelPerigo,
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable pageable)
    {

        Specification<Missao> spec = Specification.where((root, query, cb) ->
                cb.equal(root.get("organizacao").get("id"), organizacaoId)
        );

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        if (nivelPerigo != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("nivelPerigo"), nivelPerigo));
        }

        // Filtro de Intervalo de Datas
        if (inicio != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataInicio"), inicio));
        }

        if (fim != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("dataInicio"), fim));
        }

        return missaoRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    private MissaoResponse mapToResponse(Missao m)
    {
        return new MissaoResponse(
                m.getId(),
                m.getTitulo(),
                m.getNivelPerigo(),
                m.getStatus(),
                m.getDataInicio(),
                m.getDataTermino(),
                new OrganizacaoResponse(m.getOrganizacao().getId(), m.getOrganizacao().getNome())
        );
    }

    @Transactional(readOnly = true)
    public MissaoDetalheResponse obterDetalhes(Long id)
    {
        Missao m = missaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada."));

        List<AventureiroParticipanteResponse> participantes = m.getParticipacoes().stream()
                .map(p -> new AventureiroParticipanteResponse(
                        p.getAventureiro().getId(),
                        p.getAventureiro().getNome(),
                        p.getPapel(),
                        p.getRecompensaOuro(),
                        p.getDestaqueMvp()
                )).toList();

        return new MissaoDetalheResponse(
                m.getId(),
                m.getTitulo(),
                m.getNivelPerigo(),
                m.getStatus(),
                new OrganizacaoResponse(m.getOrganizacao().getId(), m.getOrganizacao().getNome()),
                participantes
        );
    }

    @Transactional(readOnly = true)
    public List<MissaoMetricasResponse> gerarRelatorioMetricas(Long organizacaoId, LocalDateTime inicio, LocalDateTime fim) {
        return missaoRepository.obterMetricasPorPeriodo(organizacaoId, inicio, fim);
    }



}