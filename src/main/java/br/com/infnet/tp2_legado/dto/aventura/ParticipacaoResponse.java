package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.enums.PapelMissao;

public record ParticipacaoResponse(
        String aventureiroNome,
        String missaoTitulo,
        PapelMissao papel,
        boolean destaqueMvp
) {}