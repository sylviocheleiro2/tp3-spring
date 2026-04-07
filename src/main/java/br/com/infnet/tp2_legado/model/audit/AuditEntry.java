package br.com.infnet.tp2_legado.model.audit;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_entries", schema = "audit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action;

    @Column(name = "entity_schema")
    private String entitySchema;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "occurred_at", insertable = false, updatable = false)
    private LocalDateTime occurredAt;

    @Column(name = "ip", columnDefinition = "inet")
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "diff", columnDefinition = "jsonb")
    private String diff;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;

    private Boolean success;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id")
    private Organizacao organizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id")
    private Usuario actorUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_api_key_id")
    private ApiKey actorApiKey;
}