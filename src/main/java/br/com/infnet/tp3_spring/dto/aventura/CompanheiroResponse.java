package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.EspecieCompanheiro;

public record CompanheiroResponse(
        Long id, //  ID do Aventureiro
        String nome,
        EspecieCompanheiro especie,
        Integer lealdade
) {}