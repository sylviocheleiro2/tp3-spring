package br.com.infnet.tp3_spring.controller.aventura;

import br.com.infnet.tp3_spring.dto.aventura.MissaoDetalheResponse;
import br.com.infnet.tp3_spring.dto.aventura.MissaoMetricasResponse;
import br.com.infnet.tp3_spring.dto.aventura.MissaoRequest;
import br.com.infnet.tp3_spring.dto.aventura.MissaoResponse;
import br.com.infnet.tp3_spring.enums.NivelPerigo;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import br.com.infnet.tp3_spring.service.aventura.MissaoService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/missoes")
@RequiredArgsConstructor
public class MissaoController {

    private final MissaoService service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MissaoRequest request)
    {
        try {
            MissaoResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<MissaoResponse>> listar(
            @RequestParam Long organizacaoId,
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) NivelPerigo nivelPerigo,
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime fim,
            Pageable pageable)
    {

        return ResponseEntity.ok(service.listarComFiltros(organizacaoId, status, nivelPerigo, inicio, fim, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDetalheResponse> obterDetalhes(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.obterDetalhes(id));
    }

    @GetMapping("/relatorios/metricas")
    public ResponseEntity<List<MissaoMetricasResponse>> relatorioMetricas(
            @RequestParam Long organizacaoId,
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime fim) {

        return ResponseEntity.ok(service.gerarRelatorioMetricas(organizacaoId, inicio, fim));
    }


}