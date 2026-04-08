package br.com.infnet.tp3_spring.service.aventura;

import br.com.infnet.tp3_spring.exceptions.BusinessRuleException;
import br.com.infnet.tp3_spring.exceptions.ResourceNotFoundException;
import br.com.infnet.tp3_spring.model.aventura.*;
import br.com.infnet.tp3_spring.repository.aventura.*;
import br.com.infnet.tp3_spring.dto.aventura.ParticipacaoRequest;
import br.com.infnet.tp3_spring.dto.aventura.ParticipacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipacaoService {

    private final ParticipacaoMissaoRepository participacaoRepository;
    private final AventureiroRepository aventureiroRepository;
    private final MissaoRepository missaoRepository;

    @Transactional
    public ParticipacaoResponse registrarParticipacao(ParticipacaoRequest request) {

        Aventureiro aventureiro = aventureiroRepository.findById(request.aventureiroId())
                .orElseThrow(() -> new ResourceNotFoundException("Aventureiro não encontrado."));

        Missao missao = missaoRepository.findById(request.missaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Missão não encontrada."));


        // O aventureiro e a missão DEVEM pertencer à mesma organização
        if (!aventureiro.getOrganizacao().getId().equals(missao.getOrganizacao().getId())) {
            throw new BusinessRuleException("VIOLAÇÃO: O aventureiro não pode participar de missões de outras organizações.");
        }

        //  Entidade de Participação com a Chave Composta
        ParticipacaoMissao participacao = ParticipacaoMissao.builder()
                .id(new ParticipacaoMissaoId(missao.getId(), aventureiro.getId()))
                .aventureiro(aventureiro)
                .missao(missao)
                .papel(request.papel())
                .recompensaOuro(request.recompensaOuro())
                .destaqueMvp(false) // Começa sempre como falso
                .build();

        participacaoRepository.save(participacao);

        return new ParticipacaoResponse(
                aventureiro.getNome(),
                missao.getTitulo(),
                participacao.getPapel(),
                participacao.getDestaqueMvp()
        );
    }
}