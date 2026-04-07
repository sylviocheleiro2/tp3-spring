package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp3_spring.enums.ClasseAventureiro;

public record AventureiroResponse(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        boolean ativo,
        OrganizacaoResponse organizacao
) {}