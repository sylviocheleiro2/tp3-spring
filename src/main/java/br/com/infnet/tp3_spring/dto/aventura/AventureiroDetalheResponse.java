package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp3_spring.enums.ClasseAventureiro;

public record AventureiroDetalheResponse(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        boolean ativo,
        OrganizacaoResponse organizacao,
        CompanheiroResponse companheiro, // Pode ser null
        long totalMissoes,
        String ultimaMissaoTitulo
) {}