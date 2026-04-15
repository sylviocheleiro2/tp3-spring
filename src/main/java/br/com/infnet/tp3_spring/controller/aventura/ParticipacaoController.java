package br.com.infnet.tp3_spring.controller.aventura;

import br.com.infnet.tp3_spring.dto.aventura.ParticipacaoRequest;
import br.com.infnet.tp3_spring.dto.aventura.ParticipacaoResponse;
import br.com.infnet.tp3_spring.service.aventura.ParticipacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participacoes")
@RequiredArgsConstructor
public class ParticipacaoController {

    private final ParticipacaoService service;

    @PostMapping
    public ResponseEntity<ParticipacaoResponse> registrar(@RequestBody ParticipacaoRequest request) {
        return ResponseEntity.ok(service.registrarParticipacao(request));
    }
}