package br.com.infnet.tp2_legado.repository.aventura;

import br.com.infnet.tp2_legado.dto.aventura.MissaoMetricasResponse;
import br.com.infnet.tp2_legado.model.aventura.Missao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long>, JpaSpecificationExecutor<Missao>
{
    // Exemplo JPQL

    @Query("""
    SELECT new br.com.infnet.tp2_legado.dto.aventura.MissaoMetricasResponse(
        m.titulo, 
        m.status, 
        m.nivelPerigo, 
        COUNT(p), 
        COALESCE(SUM(p.recompensaOuro), 0.0)
    )
    FROM Missao m
    LEFT JOIN m.participacoes p
    WHERE m.organizacao.id = :organizacaoId
    AND (CAST(:inicio AS localdatetime) IS NULL OR m.dataInicio >= :inicio)
    AND (CAST(:fim AS localdatetime) IS NULL OR m.dataInicio <= :fim)
    GROUP BY m.id, m.titulo, m.status, m.nivelPerigo
""")
    List<MissaoMetricasResponse> obterMetricasPorPeriodo(
            @Param("organizacaoId") Long organizacaoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}