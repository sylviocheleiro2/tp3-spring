package br.com.infnet.tp3_spring.repository.aventura;

import br.com.infnet.tp3_spring.model.aventura.ParticipacaoMissao;
import br.com.infnet.tp3_spring.model.aventura.ParticipacaoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {
}