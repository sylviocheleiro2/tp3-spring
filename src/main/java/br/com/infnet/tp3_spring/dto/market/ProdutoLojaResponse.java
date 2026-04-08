package br.com.infnet.tp3_spring.dto.market;

import java.math.BigDecimal;

public record ProdutoLojaResponse(
        String id,
        String nome,
        String descricao,
        String categoria,
        String raridade,
        BigDecimal preco
) {}