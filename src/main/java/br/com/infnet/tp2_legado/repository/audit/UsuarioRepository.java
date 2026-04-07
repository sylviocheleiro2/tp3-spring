package br.com.infnet.tp2_legado.repository.audit;

import br.com.infnet.tp2_legado.model.audit.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{

    Optional<Usuario> findByEmail(String email);
}