package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.enums.EspecieCompanheiro;

public record CompanheiroResponse(
        Long id, // Será o mesmo ID do Aventureiro
        String nome,
        EspecieCompanheiro especie,
        Integer lealdade
) {}