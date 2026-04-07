package br.com.infnet.tp2_legado.repository.audit;


import br.com.infnet.tp2_legado.model.audit.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
