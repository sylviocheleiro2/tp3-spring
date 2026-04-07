package br.com.infnet.tp3_spring;

import br.com.infnet.tp3_spring.model.aventura.Aventureiro;
import br.com.infnet.tp3_spring.model.aventura.Missao;
import br.com.infnet.tp3_spring.repository.aventura.AventureiroRepository;
import br.com.infnet.tp3_spring.repository.aventura.MissaoRepository;
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
class AventuraMapeamentoTest {

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Test
    @DisplayName("Deve validar a integridade entre o domínio de Aventura e o núcleo de Auditoria")
    void deveValidarIntegridadeCrossSchema() {
        // 1. Verificar se conseguimos ler aventureiros (se houver dados)
        var aventureiros = aventureiroRepository.findAll();
        System.out.println("LOG: Quantidade de aventureiros encontrados: " + aventureiros.size());

        if (!aventureiros.isEmpty()) {
            Aventureiro av = aventureiros.get(0);
            assertNotNull(av.getOrganizacao(), "O aventureiro deve estar ligado a uma organização do schema audit");
            System.out.println("LOG: Aventureiro " + av.getNome() + " pertence à " + av.getOrganizacao().getNome());

            if (av.getCompanheiro() != null) {
                System.out.println("LOG: Companheiro detectado: " + av.getCompanheiro().getNome());
            }
        }

        // 2. Verificar Missões
        var missoes = missaoRepository.findAll();
        if (!missoes.isEmpty()) {
            Missao m = missoes.get(0);
            assertNotNull(m.getOrganizacao());
            System.out.println("LOG: Missão '" + m.getTitulo() + "' validada para a organização: " + m.getOrganizacao().getNome());
        }
    }
}