package br.com.infnet.tp3_spring.model.aventura;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class ParticipacaoMissaoId implements Serializable {

    @Column(name = "missao_id")
    private Long missao_id;

    @Column(name = "aventureiro_id")
    private Long aventureiro_id;


}