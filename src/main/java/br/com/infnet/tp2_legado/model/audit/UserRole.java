package br.com.infnet.tp2_legado.model.audit;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_roles", schema = "audit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserRole {

    @EmbeddedId
    private UserRoleId id = new UserRoleId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "granted_at", insertable = false, updatable = false)
    private LocalDateTime grantedAt;
}