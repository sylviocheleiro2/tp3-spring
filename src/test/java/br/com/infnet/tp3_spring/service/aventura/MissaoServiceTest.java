package br.com.infnet.tp3_spring.service.aventura;

import br.com.infnet.tp3_spring.dto.aventura.MissaoRequest;
import br.com.infnet.tp3_spring.dto.aventura.MissaoResponse;
import br.com.infnet.tp3_spring.enums.NivelPerigo;
import br.com.infnet.tp3_spring.enums.StatusMissao;
import br.com.infnet.tp3_spring.model.audit.Organizacao;
import br.com.infnet.tp3_spring.model.aventura.Missao;
import br.com.infnet.tp3_spring.repository.audit.OrganizacaoRepository;
import br.com.infnet.tp3_spring.repository.aventura.MissaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MissaoServiceTest {

    @Mock
    private MissaoRepository missaoRepository;

    @Mock
    private OrganizacaoRepository organizacaoRepository;

    @InjectMocks
    private MissaoService missaoService;

    @Test
    void deveCriarMissaoComSucesso()
    {
        Organizacao org = Organizacao.builder().id(1L).nome("Guilda").ativo(true).build();
        Missao missaoMock = Missao.builder().id(1L).titulo("Resgate").nivelPerigo(NivelPerigo.BAIXO).status(StatusMissao.PLANEJADA).organizacao(org).build();
        
        MissaoRequest request = new MissaoRequest("Resgate", NivelPerigo.BAIXO, 1L, LocalDateTime.now());

        when(organizacaoRepository.findById(1L)).thenReturn(Optional.of(org));
        when(missaoRepository.save(any(Missao.class))).thenReturn(missaoMock);


        MissaoResponse response = missaoService.criar(request);


        assertNotNull(response);
        assertEquals("Resgate", response.titulo());
        assertEquals(StatusMissao.PLANEJADA, response.status());
    }
}