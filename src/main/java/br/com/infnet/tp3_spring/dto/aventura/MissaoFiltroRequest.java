package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.NivelPerigo;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import java.time.LocalDateTime;

public record MissaoFiltroRequest(
        Long organizacaoId,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        LocalDateTime inicio,
        LocalDateTime fim
) {}
