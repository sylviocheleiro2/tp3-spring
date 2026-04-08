package br.com.infnet.tp3_spring.service.market;

import br.com.infnet.tp3_spring.dto.market.ProdutoLojaResponse;
import br.com.infnet.tp3_spring.model.market.ProdutoLoja;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import org.springframework.data.elasticsearch.core.SearchHit;


import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketplaceService {

    private final ElasticsearchOperations elasticsearchOperations;

    // PARTE A - Buscas Textuais

     // 1. Busca por nome do produto
    public List<ProdutoLojaResponse> buscarPorNome(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m.field("nome").query(termo)))
                .build();
        return executarBusca(query);
    }

    // 2. Busca por descrição do produto padrão (match)
    public List<ProdutoLojaResponse> buscarPorDescricao(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m.field("descricao").query(termo)))
                .build();
        return executarBusca(query);
    }

    // 3. Busca por frase exata match_phrase na ordem.
    public List<ProdutoLojaResponse> buscarPorFraseExata(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.matchPhrase(m -> m.field("descricao").query(termo)))
                .build();
        return executarBusca(query);
    }

    // 4. Busca fuzzy (tolera erros de digitação)
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

    // 5. Busca em múltiplos campos simultaneamente
    public List<ProdutoLojaResponse> buscarMultiCampos(String termo) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q ->
                        q.multiMatch(m -> m
                        .fields("nome", "descricao")
                        .query(termo)
                ))
                .build();
        return executarBusca(query);
    }

    // Métodos Auxiliares

    private List<ProdutoLojaResponse> executarBusca(NativeQuery query) {
        SearchHits<ProdutoLoja> searchHits = elasticsearchOperations.search(query, ProdutoLoja.class);
        return searchHits.getSearchHits().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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
}