package br.com.zup.mercadolivre.usuarios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Representa uma senha limpa
 */
public class SenhaLimpa {

    private String senha;

    @Deprecated
    public SenhaLimpa(){
    }

    public SenhaLimpa(@NotBlank @Size(min = 6) String senha){
        Assert.hasLength(senha, "senha nao pode ser em branco");
        Assert.isTrue(senha.length() >= 6, "senha deve ter minimo 6 caracteres");
        this.senha = senha;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(senha);
    }
}