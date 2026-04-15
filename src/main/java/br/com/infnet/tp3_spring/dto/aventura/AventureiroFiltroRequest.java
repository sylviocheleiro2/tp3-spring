package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.ClasseAventureiro;

public record AventureiroFiltroRequest(
        Long organizacaoId,
        Boolean ativo,
        ClasseAventureiro classe,
        Integer nivelMinimo
) {}
