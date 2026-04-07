package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.enums.EspecieCompanheiro;

public record CompanheiroRequest(
        String nome,
        EspecieCompanheiro especie,
        Integer lealdade
) {}