package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.enums.PapelMissao;
import java.math.BigDecimal;

public record AventureiroParticipanteResponse(
        Long aventureiroId,
        String nome,
        PapelMissao papel,
        BigDecimal recompensaOuro,
        boolean destaqueMvp
) {}