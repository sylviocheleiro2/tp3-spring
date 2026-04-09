package br.com.infnet.tp3_spring.service.market;

import br.com.infnet.tp3_spring.dto.market.AggregacaoItemResponse;
import br.com.infnet.tp3_spring.dto.market.FaixaPrecoResponse;
import br.com.infnet.tp3_spring.dto.market.PrecoMedioResponse;
import br.com.infnet.tp3_spring.dto.market.ProdutoLojaResponse;
import br.com.infnet.tp3_spring.model.market.ProdutoLoja;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.RangeBucket;
import lombok.RequiredArgsConstructor;

import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketplaceService
{
    private final ElasticsearchOperations elasticsearchOperations;

    // ---  Util

    private List<ProdutoLojaResponse> executarBusca(NativeQuery query)
    {
        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);
        return hits.getSearchHits().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProdutoLojaResponse mapToResponse(SearchHit<ProdutoLoja> hit) {
        ProdutoLoja p = hit.getContent();
        return new ProdutoLojaResponse(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getCategoria(),
                p.getRaridade(),
                p.getPreco()
        );
    }

    // ---- PARTE A - Buscas Textuais

    // 1. Busca por nome do produto -> GET /produtos/busca/nome?termo=espada
    public List<ProdutoLojaResponse> buscarPorNome(String termo)
    {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m.field("nome").query(termo)))
                .build();
        return executarBusca(query);
    }

    // 2. Busca por descrição do produto padrão (match) -> GET /produtos/busca/descricao?termo=cura
    public List<ProdutoLojaResponse> buscarPorDescricao(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m.field("descricao").query(termo)))
                .build();
        return executarBusca(query);
    }

    // 3. Busca por frase exata match_phrase na ordem. -> GET /produtos/busca/frase?termo=mercado
    public List<ProdutoLojaResponse> buscarPorFraseExata(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.matchPhrase(m -> m.field("descricao").query(termo)))
                .build();
        return executarBusca(query);
    }

    // 4. Busca fuzzy (tolera erros de digitação) -> GET /produtos/busca/fuzzy?termo=espa
    public List<ProdutoLojaResponse> buscarFuzzyNome(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m
                        .field("nome")
                        .query(termo)
                        .fuzziness("AUTO")
                ))
                .build();
        return executarBusca(query);
    }

    // 5. Busca em múltiplos campos simultaneamente -> GET /produtos/busca/multicampos?termo=dragao
    public List<ProdutoLojaResponse> buscarMultiCampos(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m
                        .fields("nome", "descricao")
                        .query(termo)
                ))
                .build();
        return executarBusca(query);
    }



    // ----  PARTE B - Buscas com Filtros

    // 1. Busca textual + filtro por categoria -> GET /produtos/busca/com-filtro?termo=pocao&categoria=pocoes
    public List<ProdutoLojaResponse> buscarComFiltro(String termo, String categoria)
    {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b
                        .must(m -> m.match(ma -> ma.field("descricao").query(termo)))
                        .filter(f -> f.term(t -> t.field("categoria").value(categoria)))
                ))
                .build();
        return executarBusca(query);
    }

    // 2. Busca por faixa de preço -> GET /produtos/busca/faixa-preco?min=50&max=300
    public List<ProdutoLojaResponse> buscarPorFaixaPreco(BigDecimal min, BigDecimal max)
    {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.range(r -> r
                        .number(n -> n
                                .field("preco")
                                .gte(min.doubleValue())
                                .lte(max.doubleValue())
                        )
                ))
                .build();
        return executarBusca(query);
    }

    // 3. Busca combinada: categoria + raridade + faixa de preço -> GET /produtos/busca/avancada?categoria=armas&raridade=raro&min=200&max=1000
    public List<ProdutoLojaResponse> buscarAvancada(String categoria, String raridade,
                                                    BigDecimal min, BigDecimal max)
    {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b
                        .filter(f -> f.term(t -> t.field("categoria").value(categoria)))
                        .filter(f -> f.term(t -> t.field("raridade").value(raridade)))
                        .filter(f -> f.range(r -> r
                                .number(n -> n
                                        .field("preco")
                                        .gte(min.doubleValue())
                                        .lte(max.doubleValue())
                                )
                        ))
                ))
                .build();
        return executarBusca(query);
    }

    // ── Parte C  Agregações ------

    // GET /produtos/agregacoes/por-categoria
    public List<AggregacaoItemResponse> agregarPorCategoria()
    {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("por_categoria",
                        Aggregation.of(a -> a.terms(t -> t.field("categoria").size(50))))
                .withQuery(q -> q.matchAll(m -> m))
                .withMaxResults(0)
                .build();

        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);
        ElasticsearchAggregations aggs = (ElasticsearchAggregations) hits.getAggregations();

        return aggs.get("por_categoria")
                .aggregation()
                .getAggregate()
                .sterms()
                .buckets().array().stream()
                .map(b -> new AggregacaoItemResponse(b.key().stringValue(), b.docCount()))
                .toList();
    }

    // GET /produtos/agregacoes/por-raridade
    public List<AggregacaoItemResponse> agregarPorRaridade() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("por_raridade",
                        Aggregation.of(a -> a.terms(t -> t.field("raridade").size(50))))
                .withQuery(q -> q.matchAll(m -> m))
                .withMaxResults(0)
                .build();

        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);
        ElasticsearchAggregations aggs = (ElasticsearchAggregations) hits.getAggregations();

        return aggs.get("por_raridade")
                .aggregation()
                .getAggregate()
                .sterms()
                .buckets().array().stream()
                .map(b -> new AggregacaoItemResponse(b.key().stringValue(), b.docCount()))
                .toList();
    }

    // GET /produtos/agregacoes/preco-medio
    public PrecoMedioResponse calcularPrecoMedio() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("preco_medio",
                        Aggregation.of(a -> a.avg(avg -> avg.field("preco"))))
                .withQuery(q -> q.matchAll(m -> m))
                .withMaxResults(0)
                .build();

        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);
        ElasticsearchAggregations aggs = (ElasticsearchAggregations) hits.getAggregations();

        double media = aggs.get("preco_medio")
                .aggregation()
                .getAggregate()
                .avg()
                .value();

        return new PrecoMedioResponse(media);
    }

    // GET /produtos/agregacoes/faixas-preco
    public List<FaixaPrecoResponse> agregarPorFaixaPreco() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("faixas_preco",
                        Aggregation.of(a -> a.range(r -> r
                                .field("preco")
                                .ranges(ra -> ra.to(100.0))
                                .ranges(ra -> ra.from(100.0).to(300.0))
                                .ranges(ra -> ra.from(300.0).to(700.0))
                                .ranges(ra -> ra.from(700.0))
                        )))
                .withQuery(q -> q.matchAll(m -> m))
                .withMaxResults(0)
                .build();

        SearchHits<ProdutoLoja> hits = elasticsearchOperations.search(query, ProdutoLoja.class);
        ElasticsearchAggregations aggs = (ElasticsearchAggregations) hits.getAggregations();

        List<String> labels = List.of(
                "Abaixo de 100",
                "De 100 a 300",
                "De 300 a 700",
                "Acima de 700"
        );

        List<RangeBucket> buckets = aggs.get("faixas_preco")
                .aggregation()
                .getAggregate()
                .range()
                .buckets().array();

        List<FaixaPrecoResponse> resultado = new ArrayList<>();
        for (int i = 0; i < buckets.size(); i++) {
            resultado.add(new FaixaPrecoResponse(labels.get(i), buckets.get(i).docCount()));
        }
        return resultado;
    }

}