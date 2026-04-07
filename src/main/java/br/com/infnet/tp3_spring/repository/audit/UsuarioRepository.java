package br.com.infnet.tp3_spring.repository.audit;

import br.com.infnet.tp3_spring.model.audit.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{

    Optional<Usuario> findByEmail(String email);
}