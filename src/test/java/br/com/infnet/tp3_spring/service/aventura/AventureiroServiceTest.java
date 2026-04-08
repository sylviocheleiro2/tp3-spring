package br.com.infnet.tp3_spring.service.aventura;

import br.com.infnet.tp3_spring.dto.aventura.AventureiroRequest;
import br.com.infnet.tp3_spring.dto.aventura.AventureiroResponse;
import br.com.infnet.tp3_spring.enums.ClasseAventureiro;
import br.com.infnet.tp3_spring.model.audit.Organizacao;
import br.com.infnet.tp3_spring.model.audit.Usuario;
import br.com.infnet.tp3_spring.model.aventura.Aventureiro;
import br.com.infnet.tp3_spring.repository.audit.OrganizacaoRepository;
import br.com.infnet.tp3_spring.repository.audit.UsuarioRepository;
import br.com.infnet.tp3_spring.repository.aventura.AventureiroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AventureiroServiceTest {

    @Mock
    private AventureiroRepository aventureiroRepository;

    @Mock
    private OrganizacaoRepository organizacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AventureiroService aventureiroService;

    @Test
    void deveCadastrarAventureiroComSucesso()
    {
        Organizacao org = Organizacao.builder().id(1L).nome("Guilda").ativo(true).build();
        Usuario usuario = Usuario.builder().id(1L).organizacao(org).build();
        Aventureiro aventureiroMock = Aventureiro.builder().id(1L).nome("Arthur").classe(ClasseAventureiro.GUERREIRO).nivel(1).ativo(true).organizacao(org).build();
        
        AventureiroRequest request = new AventureiroRequest("Arthur", ClasseAventureiro.GUERREIRO, 1, 1L, 1L);

        when(organizacaoRepository.findById(1L)).thenReturn(Optional.of(org));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(aventureiroRepository.save(any(Aventureiro.class))).thenReturn(aventureiroMock);


        AventureiroResponse response = aventureiroService.cadastrar(request);


        assertNotNull(response);
        assertEquals("Arthur", response.nome());
        assertEquals(ClasseAventureiro.GUERREIRO, response.classe());
    }
}