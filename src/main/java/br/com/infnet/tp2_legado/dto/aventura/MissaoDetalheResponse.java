package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp2_legado.enums.NivelPerigo;
import br.com.infnet.tp2_legado.enums.StatusMissao;
import java.util.List;

public record MissaoDetalheResponse(
        Long id,
        String titulo,
        NivelPerigo nivelPerigo,
        StatusMissao status,
        OrganizacaoResponse organizacao,
        List<AventureiroParticipanteResponse> participantes // Nome atualizado aqui
) {}