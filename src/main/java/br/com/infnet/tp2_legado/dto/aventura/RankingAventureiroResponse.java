package br.com.infnet.tp2_legado.dto.aventura;

import java.math.BigDecimal;

public record RankingAventureiroResponse(
        String nome,
        Long totalParticipacoes,
        BigDecimal totalRecompensa,
        Long totalDestaques
) {

    public RankingAventureiroResponse(String nome, Long totalParticipacoes, Object totalRecompensa, Long totalDestaques)
    {
        this(
                nome,
                totalParticipacoes,
                totalRecompensa instanceof Double d ? BigDecimal.valueOf(d) : (BigDecimal) totalRecompensa,
                totalDestaques
        );
    }
}