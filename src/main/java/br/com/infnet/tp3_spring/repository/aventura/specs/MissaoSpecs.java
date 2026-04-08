package br.com.infnet.tp3_spring.repository.aventura.specs;

import br.com.infnet.tp3_spring.enums.NivelPerigo;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import br.com.infnet.tp3_spring.model.aventura.Missao;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MissaoSpecs {

    public static Specification<Missao> comFiltros(
            Long organizacaoId,
            StatusMissao status,
            NivelPerigo nivelPerigo,
            LocalDateTime inicio,
            LocalDateTime fim) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("organizacao").get("id"), organizacaoId));

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (nivelPerigo != null) {
                predicates.add(cb.equal(root.get("nivelPerigo"), nivelPerigo));
            }
            if (inicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataInicio"), inicio));
            }
            if (fim != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataInicio"), fim));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
