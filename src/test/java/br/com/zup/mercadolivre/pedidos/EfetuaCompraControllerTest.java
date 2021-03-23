package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.usuarios.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class EfetuaCompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails("fulano@email.com")
    @DisplayName("deve criar uma compra")
    void teste() throws Exception {
        CompraRequest request = new CompraRequest(2, 3L, GatewayPagamento.PAYPAL);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/compras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("fulano@email.com")
    @DisplayName("nao deve criar uma compra com produto que nao existe")
    void teste2() throws Exception {
        CompraRequest request = new CompraRequest(2, 10L, GatewayPagamento.PAYPAL);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/compras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}