package br.com.infnet.tp3_spring.controller.aventura;

import br.com.infnet.tp3_spring.dto.audit.OrganizacaoResponse;
import br.com.infnet.tp3_spring.dto.aventura.AventureiroRequest;
import br.com.infnet.tp3_spring.dto.aventura.AventureiroResponse;
import br.com.infnet.tp3_spring.enums.ClasseAventureiro;
import br.com.infnet.tp3_spring.service.aventura.AventureiroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AventureiroController.class)
class AventureiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AventureiroService aventureiroService;

    @Test
    void deveCriarAventureiroERetornarStatus201() throws Exception {

        AventureiroRequest request = new AventureiroRequest("Lancelot", ClasseAventureiro.GUERREIRO, 5, 1L, 1L);
        AventureiroResponse response = new AventureiroResponse(1L, "Lancelot", ClasseAventureiro.GUERREIRO, 5, true, new OrganizacaoResponse(1L, "Guilda"));

        when(aventureiroService.cadastrar(any(AventureiroRequest.class))).thenReturn(response);


        mockMvc.perform(post("/api/aventureiros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Lancelot"))
                .andExpect(jsonPath("$.classe").value("GUERREIRO"));
    }
}