package br.com.zup.mercadolivre.usuarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um novo usuário")
    void criaUsuario() throws Exception {
        UsuarioRequest usuarioRequest = new UsuarioRequest("teste@teste.com", "123456");
        /**
         *      Perform() primeiro passo para iniciar o teste
         *      Informar o MockMvcRequestBuilder para indicar o método
         *      Informar o content em casos de post
         *      MediaType contém um ENUM com os tipos aceitos para content types
         */
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/cadastro")
                        .content(objectMapper.writeValueAsString(usuarioRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Não deve criar um usuário com email repetido")
    void umUsuarioPorEmail() throws Exception {
        UsuarioRequest usuarioRequest = new UsuarioRequest("teste@teste.com", "123456");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/cadastro")
                        .content(objectMapper.writeValueAsString(usuarioRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/cadastro")
                        .content(objectMapper.writeValueAsString(usuarioRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}