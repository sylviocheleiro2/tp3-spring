package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp3_spring.enums.NivelPerigo;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import java.util.List;

public record MissaoDetalheResponse(
        Long id,
        String titulo,
        NivelPerigo nivelPerigo,
        StatusMissao status,
        OrganizacaoResponse organizacao,
        List<AventureiroParticipanteResponse> participantes // Nome atualizado aqui
) {}