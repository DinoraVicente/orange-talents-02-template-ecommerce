package br.com.zup.mercadolivre.usuarios;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class UsuarioTest {

    @Test
    @DisplayName("Deveria criar um usuário")
    void deveriaCriarUsuario() {
        Assertions.assertDoesNotThrow(() -> new Usuario("fulano@email.com", new SenhaLimpa("123456")));
    }

    @Test
    @DisplayName("Deveria retornar erro ao passar email nulo")
    void deveriaRetornarErroAoTentarCriarUsuarioComEmailNulo() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Usuario(null, new SenhaLimpa("123456")));
    }

    @Test
    @DisplayName("Não deveria permitir senhas menores que 6 caracteres")
    void naoDeveriaPermitirPasswordMenorQue6Caracteres() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Usuario("fulano@email.come", new SenhaLimpa("12345")));
    }
}