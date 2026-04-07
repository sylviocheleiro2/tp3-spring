package br.com.infnet.tp2_legado.repository.aventura;

import br.com.infnet.tp2_legado.model.aventura.ParticipacaoMissao;
import br.com.infnet.tp2_legado.model.aventura.ParticipacaoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {
}