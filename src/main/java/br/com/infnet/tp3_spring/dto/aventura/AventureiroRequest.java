package br.com.infnet.tp3_spring.dto.aventura;

import br.com.infnet.tp3_spring.enums.ClasseAventureiro;

//  Regra: "Todos os registros devem estar associados a uma organização".
public record AventureiroRequest(
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        Long organizacaoId, // Chave para a regra de associação obrigatória
        Long usuarioResponsavelId // Chave para validar a regra de "Não permitir relacionamento cruzado"
) {}