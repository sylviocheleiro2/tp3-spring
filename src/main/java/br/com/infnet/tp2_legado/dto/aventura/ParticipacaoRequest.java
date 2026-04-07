package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.enums.PapelMissao;
import java.math.BigDecimal;

public record ParticipacaoRequest(
        Long aventureiroId,
        Long missaoId,
        PapelMissao papel,
        BigDecimal recompensaOuro
) {}