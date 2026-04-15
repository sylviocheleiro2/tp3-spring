package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp3_spring.enums.NivelPerigo;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import java.time.LocalDateTime;

public record MissaoResponse(
        Long id,
        String titulo,
        NivelPerigo nivelPerigo,
        StatusMissao status,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino,
        OrganizacaoResponse organizacao
) {}