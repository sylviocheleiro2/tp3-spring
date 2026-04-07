package br.com.infnet.tp2_legado.repository.aventura;

import br.com.infnet.tp2_legado.model.aventura.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanheiroRepository extends JpaRepository<Companheiro, Long> {
}