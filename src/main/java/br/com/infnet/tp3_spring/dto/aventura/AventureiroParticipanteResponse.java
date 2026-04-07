package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.PapelMissao;
import java.math.BigDecimal;

public record AventureiroParticipanteResponse(
        Long aventureiroId,
        String nome,
        PapelMissao papel,
        BigDecimal recompensaOuro,
        boolean destaqueMvp
) {}