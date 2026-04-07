package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.PapelMissao;

public record ParticipacaoResponse(
        String aventureiroNome,
        String missaoTitulo,
        PapelMissao papel,
        boolean destaqueMvp
) {}