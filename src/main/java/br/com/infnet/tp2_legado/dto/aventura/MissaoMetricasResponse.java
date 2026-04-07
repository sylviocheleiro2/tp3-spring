package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.enums.NivelPerigo;
import br.com.infnet.tp2_legado.enums.StatusMissao;
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