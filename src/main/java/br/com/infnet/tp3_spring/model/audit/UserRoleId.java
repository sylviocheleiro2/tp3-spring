package br.com.infnet.tp3_spring.model.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "role_id")
    private Long roleId;

    // @EqualsAndHashCode SE QUEBRAR ALGO DESCOMENTAR E TIRAR O @EQUALSHASHCODE

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserRoleId that = (UserRoleId) o;
//        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(roleId, that.roleId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(usuarioId, roleId);
//    }


}