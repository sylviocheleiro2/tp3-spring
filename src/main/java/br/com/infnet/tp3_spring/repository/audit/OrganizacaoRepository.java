package br.com.infnet.tp3_spring.repository.audit;

import br.com.infnet.tp3_spring.model.audit.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {
}
