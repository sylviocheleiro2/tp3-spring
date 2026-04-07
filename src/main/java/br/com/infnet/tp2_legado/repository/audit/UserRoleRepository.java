package br.com.infnet.tp2_legado.repository.audit;

import br.com.infnet.tp2_legado.model.audit.UserRole;
import br.com.infnet.tp2_legado.model.audit.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}