package br.com.infnet.tp3_spring.repository.audit;

import br.com.infnet.tp3_spring.model.audit.UserRole;
import br.com.infnet.tp3_spring.model.audit.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}