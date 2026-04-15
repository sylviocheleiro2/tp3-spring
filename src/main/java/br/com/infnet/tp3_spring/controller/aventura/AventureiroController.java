package br.com.infnet.tp3_spring.controller.aventura;

import br.com.infnet.tp3_spring.dto.aventura.*;
import br.com.infnet.tp3_spring.enums.ClasseAventureiro;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import br.com.infnet.tp3_spring.service.aventura.AventureiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/aventureiros")
@RequiredArgsConstructor
public class AventureiroController {

    private final AventureiroService service;

    @PostMapping
    public ResponseEntity<AventureiroResponse> criar(@RequestBody AventureiroRequest request)
    {
        AventureiroResponse response = service.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AventureiroResponse>> listar(
            @ModelAttribute AventureiroFiltroRequest filtro,
            Pageable pageable)
    {
        // Aceita ?page=0&size=10&sort=nome,asc
        Page<AventureiroResponse> pagina = service.listarComFiltros(filtro, pageable);

        return ResponseEntity.ok(pagina);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
    {
        service.deletar(id);
        return ResponseEntity.noContent().build(); // Status 204
    }

    @PostMapping("/{id}/companheiro")
    public ResponseEntity<CompanheiroResponse> adotarCompanheiro(@PathVariable Long id, @RequestBody CompanheiroRequest request)
    {
        CompanheiroResponse response = service.adotarCompanheiro(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<AventureiroResponse>> buscarPorNome(
            @RequestParam Long organizacaoId,
            @RequestParam String nome,
            Pageable pageable)
    {

        return ResponseEntity.ok(service.buscarPorNome(organizacaoId, nome, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroDetalheResponse> obterDetalhes(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.obterDetalhes(id));
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingAventureiroResponse>> getRanking(
            @RequestParam Long organizacaoId,
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime fim) {

        return ResponseEntity.ok(service.gerarRanking(organizacaoId, status, inicio, fim));
    }

}