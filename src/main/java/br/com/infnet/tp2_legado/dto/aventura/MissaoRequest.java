package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.enums.NivelPerigo;
import java.time.LocalDateTime;

public record MissaoRequest(
        String titulo,
        NivelPerigo nivelPerigo,
        Long organizacaoId,
        LocalDateTime dataInicio // Pode ser nulo se a missão for apenas planejada
) {}