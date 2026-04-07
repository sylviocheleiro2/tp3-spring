package br.com.infnet.tp2_legado.dto.aventura;

import br.com.infnet.tp2_legado.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp2_legado.enums.ClasseAventureiro;
import java.util.Optional;

public record AventureiroDetalheResponse(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        boolean ativo,
        OrganizacaoResponse organizacao,
        CompanheiroResponse companheiro, // Pode ser null
        long totalMissoes,
        String ultimaMissaoTitulo
) {}