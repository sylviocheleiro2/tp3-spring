package br.com.infnet.tp2_legado.repository.audit;

import br.com.infnet.tp2_legado.model.audit.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    // JPQL customizada para carregar as permissões de forma eficiente
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions")
    List<Role> findAllWithPermissions();
}