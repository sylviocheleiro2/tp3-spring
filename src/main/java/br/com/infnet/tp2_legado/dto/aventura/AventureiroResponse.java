package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp2_legado.enums.ClasseAventureiro;

public record AventureiroResponse(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        boolean ativo,
        OrganizacaoResponse organizacao
) {}