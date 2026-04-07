package br.com.infnet.tp2_legado.controller.aventura;

import br.com.infnet.tp2_legado.dto.aventura.ParticipacaoRequest;
import br.com.infnet.tp2_legado.service.aventura.ParticipacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participacoes")
@RequiredArgsConstructor
public class ParticipacaoController {

    private final ParticipacaoService service;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody ParticipacaoRequest request) {
        try {
            return ResponseEntity.ok(service.registrarParticipacao(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}