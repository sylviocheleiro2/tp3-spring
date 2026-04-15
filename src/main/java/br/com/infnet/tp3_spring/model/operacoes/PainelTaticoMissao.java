package br.com.infnet.tp3_spring.model.operacoes;

import br.com.infnet.tp3_spring.enums.NivelPerigo;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vw_painel_tatico_missao", schema = "operacoes")
@Getter@Setter
@Immutable
public class PainelTaticoMissao implements Serializable {

   @Id
   @Column(name = "missao_id")
   private Long missaoId;

   @Column(name = "titulo")
   private String titulo;

   @Column(name = "status")
   @Enumerated(EnumType.STRING)
   StatusMissao status;

   @Column(name = "nivel_perigo")
   @Enumerated(EnumType.STRING)
   NivelPerigo nivelPerigo;

   @Column(name = "organizacao_id")
   private Long organizacaoId;

   @Column(name = "total_participantes")
   private Long totalParticipantes;

   @Column(name = "nivel_medio_equipe")
   private BigDecimal nivelMedioEquipe;

   @Column(name = "total_recompensa")
   private BigDecimal totalRecompensa;

   @Column(name = "total_mvps")
   private Long totalMvps;

   @Column(name = "participantes_com_companheiro")
   private Long participantesComCompanheiro;

   @Column(name = "ultima_atualizacao")
   private LocalDateTime ultimaAtualizacao;
    
   @Column(name = "indice_prontidao")
   private BigDecimal indiceProntidao;
}
