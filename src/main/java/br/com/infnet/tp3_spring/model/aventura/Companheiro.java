package br.com.infnet.tp3_spring.model.aventura;

import br.com.infnet.tp3_spring.enums.EspecieCompanheiro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companheiros", schema = "aventura")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Companheiro {

    @Id
    private Long id; // Este ID será preenchido pelo ID do Aventureiro

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EspecieCompanheiro especie;

    @Column(nullable = false)
    private Integer lealdade;

    @OneToOne
    @MapsId // Mágica aqui: diz que o ID desta classe é o mesmo do Aventureiro
    @JoinColumn(name = "aventureiro_id")
    private Aventureiro aventureiro;
}