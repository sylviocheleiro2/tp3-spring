package br.com.infnet.tp2_legado;

import br.com.infnet.tp2_legado.model.audit.Usuario;
import br.com.infnet.tp2_legado.repository.audit.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class MapeamentoTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve validar se o usuário, organização, roles e permissões estão integrados")
    void deveValidarMapeamentoCompleto() {
        // 1. Tenta buscar o primeiro usuário do banco legado
        Usuario usuario = usuarioRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Banco legado está vazio! Insira um dado via SQL para testar."));

        // 2. Teste: Relacionamento com Organização
        assertNotNull(usuario.getOrganizacao(), "O usuário deve pertencer a uma organização");
        System.out.println("LOG: Organização do usuário: " + usuario.getOrganizacao().getNome());

        // 3. Teste: Roles são carregadas corretamente?
        assertNotNull(usuario.getRoles(), "A lista de roles não deve ser nula");
        assertFalse(usuario.getRoles().isEmpty(), "O usuário do teste deve ter ao menos uma Role vinculada");

        usuario.getRoles().forEach(userRole -> {
            var role = userRole.getRole();
            System.out.println("LOG: Role encontrada: " + role.getNome());

            // 4. Teste: Permissões estão acessíveis via roles?
            assertNotNull(role.getPermissions(), "A role deve ter uma lista de permissões");
            role.getPermissions().forEach(perm -> {
                System.out.println("LOG: Permissão da Role: " + perm.getCode());
                assertNotNull(perm.getCode());
            });
        });
    }
}