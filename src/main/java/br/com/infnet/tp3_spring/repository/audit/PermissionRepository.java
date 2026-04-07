package br.com.infnet.tp3_spring.repository.audit;


import br.com.infnet.tp3_spring.model.audit.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
