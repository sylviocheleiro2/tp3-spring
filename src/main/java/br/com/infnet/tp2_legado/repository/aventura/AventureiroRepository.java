package br.com.infnet.tp2_legado.repository.aventura;

import br.com.infnet.tp2_legado.dto.aventura.RankingAventureiroResponse;
import br.com.infnet.tp2_legado.enums.StatusMissao;
import br.com.infnet.tp2_legado.model.aventura.Aventureiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long>, JpaSpecificationExecutor<Aventureiro>
{
    @Query("""
    SELECT new br.com.infnet.tp2_legado.dto.aventura.RankingAventureiroResponse(
        a.nome,
        COUNT(p),
        COALESCE(SUM(p.recompensaOuro), 0.0),
        COUNT(CASE WHEN p.destaqueMvp = true THEN 1 END)
    )
    FROM Aventureiro a
    JOIN a.participacoes p
    JOIN p.missao m
    WHERE a.organizacao.id = :organizacaoId
    AND (CAST(:status AS string) IS NULL OR m.status = :status)
    AND (CAST(:inicio AS localdatetime) IS NULL OR m.dataInicio >= :inicio)
    AND (CAST(:fim AS localdatetime) IS NULL OR m.dataInicio <= :fim)
    GROUP BY a.id, a.nome
    ORDER BY COUNT(p) DESC, SUM(p.recompensaOuro) DESC
""")
    List<RankingAventureiroResponse> obterRanking(
            @Param("organizacaoId") Long organizacaoId,
            @Param("status") StatusMissao status,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}