package br.com.infnet.tp3_spring.model.aventura;

import br.com.infnet.tp3_spring.enums.PapelMissao;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "participacoes_missao", schema = "aventura")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ParticipacaoMissao {

    @EmbeddedId
    private ParticipacaoMissaoId id = new ParticipacaoMissaoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("missao_id")
    @JoinColumn(name = "missao_id")
    private Missao missao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("aventureiro_id")
    @JoinColumn(name = "aventureiro_id")
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PapelMissao papel;

    @Column(name = "recompensa_ouro")
    private BigDecimal recompensaOuro;

    @Column(name = "destaque_mvp", nullable = false)
    private Boolean destaqueMvp;

    @Column(name = "data_registro", insertable = false, updatable = false)
    private LocalDateTime dataRegistro;
}