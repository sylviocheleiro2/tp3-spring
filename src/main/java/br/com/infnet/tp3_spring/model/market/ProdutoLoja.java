package br.com.infnet.tp3_spring.model.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "guilda_loja", createIndex = false) // Não alterar/criar o índice
public class ProdutoLoja {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "portuguese_custom")
    private String nome;

    @Field(type = FieldType.Text, analyzer = "portuguese_custom")
    private String descricao;

    @Field(type = FieldType.Keyword)
    private String categoria;

    @Field(type = FieldType.Keyword)
    private String raridade;

    @Field(type = FieldType.Float) // No ES é float, no Java usamos BigDecimal para precisão financeira
    private BigDecimal preco;
}