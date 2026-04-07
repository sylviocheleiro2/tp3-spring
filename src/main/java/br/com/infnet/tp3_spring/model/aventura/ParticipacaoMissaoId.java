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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ParticipacaoMissaoId that = (ParticipacaoMissaoId) o;
//        return Objects.equals(missao_id, that.missao_id) &&
//                Objects.equals(aventureiro_id, that.aventureiro_id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(missao_id, aventureiro_id);
//    }

}