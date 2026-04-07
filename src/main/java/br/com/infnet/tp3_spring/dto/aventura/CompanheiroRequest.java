package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.EspecieCompanheiro;

public record CompanheiroRequest(
        String nome,
        EspecieCompanheiro especie,
        Integer lealdade
) {}