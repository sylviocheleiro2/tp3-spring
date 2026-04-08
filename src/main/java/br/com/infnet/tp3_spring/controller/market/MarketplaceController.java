package br.com.infnet.tp3_spring.controller.market;

import br.com.infnet.tp3_spring.dto.market.ProdutoLojaResponse;
import br.com.infnet.tp3_spring.service.market.MarketplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class MarketplaceController {

    private final MarketplaceService marketplaceService;

    // PARTE A - Buscas Textuais

    @GetMapping("/busca/nome")
    public ResponseEntity<List<ProdutoLojaResponse>> buscarPorNome(@RequestParam String termo) {
        return ResponseEntity.ok(marketplaceService.buscarPorNome(termo));
    }

    @GetMapping("/busca/descricao")
    public ResponseEntity<List<ProdutoLojaResponse>> buscarPorDescricao(@RequestParam String termo) {
        return ResponseEntity.ok(marketplaceService.buscarPorDescricao(termo));
    }

    @GetMapping("/busca/frase")
    public ResponseEntity<List<ProdutoLojaResponse>> buscarPorFrase(@RequestParam String termo) {
        return ResponseEntity.ok(marketplaceService.buscarPorFraseExata(termo));
    }

    @GetMapping("/busca/fuzzy")
    public ResponseEntity<List<ProdutoLojaResponse>> buscarFuzzy(@RequestParam String termo) {
        return ResponseEntity.ok(marketplaceService.buscarFuzzyNome(termo));
    }

    @GetMapping("/busca/multicampos")
    public ResponseEntity<List<ProdutoLojaResponse>> buscarMultiCampos(@RequestParam String termo) {
        return ResponseEntity.ok(marketplaceService.buscarMultiCampos(termo));
    }
}