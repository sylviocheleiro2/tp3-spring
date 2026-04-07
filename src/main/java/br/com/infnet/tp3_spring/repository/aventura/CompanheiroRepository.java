package br.com.infnet.tp3_spring.repository.aventura;

import br.com.infnet.tp3_spring.model.aventura.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanheiroRepository extends JpaRepository<Companheiro, Long> {
}