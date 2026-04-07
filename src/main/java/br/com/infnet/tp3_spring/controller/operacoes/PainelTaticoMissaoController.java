package br.com.infnet.tp3_spring.controller.operacoes;

import br.com.infnet.tp3_spring.model.operacoes.PainelTaticoMissao;
import br.com.infnet.tp3_spring.service.operacoes.PainelTaticoMissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/operacoes/painel-tatico")
public class PainelTaticoMissaoController {

    private final PainelTaticoMissaoService service;

    @GetMapping("/missoes/todos")
    public ResponseEntity<List<PainelTaticoMissao>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/missoes/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> listarTop10Ultimos15Dias() {
        return ResponseEntity.ok(service.listarTop10Ultimos15Dias());
    }
}