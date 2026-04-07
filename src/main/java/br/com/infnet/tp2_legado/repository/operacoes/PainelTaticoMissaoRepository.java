package br.com.infnet.tp2_legado.repository.operacoes;

import br.com.infnet.tp2_legado.model.operacoes.PainelTaticoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {

//    Query Derivada
    List<PainelTaticoMissao> findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(LocalDateTime data);
}