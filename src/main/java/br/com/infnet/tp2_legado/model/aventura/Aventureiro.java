package br.com.infnet.tp2_legado.model.aventura;

import br.com.infnet.tp2_legado.enums.ClasseAventureiro;
import br.com.infnet.tp2_legado.model.audit.Organizacao;
import br.com.infnet.tp2_legado.model.audit.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import java.util.Set;

@Entity
@Table(name = "aventureiros", schema = "aventura")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Aventureiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ClasseAventureiro classe;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "aventureiro")
    private Set<ParticipacaoMissao> participacoes;

    // RELACIONAMENTOS CROSS-SCHEMA

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioResponsavel; // O usuário que cadastrou o aventureiro

    // Relacionamento 1:1 com Companheiro (Mapearemos a seguir)
    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Companheiro companheiro;
}