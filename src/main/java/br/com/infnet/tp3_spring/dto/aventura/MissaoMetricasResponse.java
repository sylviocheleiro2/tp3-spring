package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.NivelPerigo;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import java.math.BigDecimal;

public record MissaoMetricasResponse(
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        Long quantidadeParticipantes,
        BigDecimal totalRecompensas
) {

    public MissaoMetricasResponse(String titulo, StatusMissao status, NivelPerigo nivelPerigo, Long quantidadeParticipantes, Object totalRecompensas) {
        this(
                titulo,
                status,
                nivelPerigo,
                quantidadeParticipantes,
                totalRecompensas instanceof Double d ? BigDecimal.valueOf(d) : (BigDecimal) totalRecompensas
        );
    }
}