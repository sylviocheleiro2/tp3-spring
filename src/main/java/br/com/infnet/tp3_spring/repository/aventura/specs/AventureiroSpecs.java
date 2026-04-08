package br.com.infnet.tp3_spring.repository.aventura.specs;

import br.com.infnet.tp3_spring.enums.ClasseAventureiro;
import br.com.infnet.tp3_spring.model.aventura.Aventureiro;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AventureiroSpecs {

    public static Specification<Aventureiro> comFiltros(
            Long organizacaoId,
            Boolean ativo,
            ClasseAventureiro classe,
            Integer nivelMinimo)
    {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("organizacao").get("id"), organizacaoId));

            if (ativo != null) {
                predicates.add(cb.equal(root.get("ativo"), ativo));
            }
            if (classe != null) {
                predicates.add(cb.equal(root.get("classe"), classe));
            }
            if (nivelMinimo != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("nivel"), nivelMinimo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Aventureiro> porNome(Long organizacaoId, String nome)
    {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("organizacao").get("id"), organizacaoId));

            if (nome != null && !nome.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
