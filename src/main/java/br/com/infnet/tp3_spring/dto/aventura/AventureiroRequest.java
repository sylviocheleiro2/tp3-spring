package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.ClasseAventureiro;

public record AventureiroRequest(
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        Long organizacaoId,
        Long usuarioResponsavelId
) {}