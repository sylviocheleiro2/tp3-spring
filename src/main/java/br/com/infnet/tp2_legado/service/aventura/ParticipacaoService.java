package br.com.infnet.tp2_legado.service.aventura;

import br.com.infnet.tp2_legado.model.aventura.*;
import br.com.infnet.tp2_legado.repository.aventura.*;
import br.com.infnet.tp2_legado.dto.aventura.ParticipacaoRequest;
import br.com.infnet.tp2_legado.dto.aventura.ParticipacaoResponse;
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
        // 1. Localizar as peças do tabuleiro
        Aventureiro aventureiro = aventureiroRepository.findById(request.aventureiroId())
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado."));

        Missao missao = missaoRepository.findById(request.missaoId())
                .orElseThrow(() -> new RuntimeException("Missão não encontrada."));

        // 2. REGRA DE OURO: Restrição de Organização
        // O aventureiro e a missão DEVEM pertencer à mesma organização
        if (!aventureiro.getOrganizacao().getId().equals(missao.getOrganizacao().getId())) {
            throw new RuntimeException("VIOLAÇÃO: O aventureiro não pode participar de missões de outras organizações.");
        }

        // 3. Criar a Entidade de Participação com a Chave Composta
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