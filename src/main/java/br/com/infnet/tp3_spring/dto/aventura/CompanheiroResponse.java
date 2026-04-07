package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.EspecieCompanheiro;

public record CompanheiroResponse(
        Long id, // Será o mesmo ID do Aventureiro
        String nome,
        EspecieCompanheiro especie,
        Integer lealdade
) {}