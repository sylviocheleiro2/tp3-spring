package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.PapelMissao;
import java.math.BigDecimal;

public record ParticipacaoRequest(
        Long aventureiroId,
        Long missaoId,
        PapelMissao papel,
        BigDecimal recompensaOuro
) {}