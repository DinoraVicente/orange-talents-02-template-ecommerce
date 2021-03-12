package br.com.zup.mercadolivre.usuarios;

import br.com.zup.mercadolivre.config.validacoes.UnicoValor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UsuarioRequest {

    @Email
    @NotBlank
    @UnicoValor(domainClass = Usuario.class, fieldName = "login")
    private String login;
    @NotBlank
    @Size(min = 6)
    private String senha;
    private LocalDate instanteCriacao = LocalDate.now();

    @Deprecated
    public UsuarioRequest(){
    }

    public UsuarioRequest(@Email @NotBlank String login,
                          @NotBlank @Size(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getInstanteCriacao() {
        return instanteCriacao;
    }

    public Usuario toModel() {
        return new Usuario(login, new SenhaLimpa(senha), instanteCriacao);
    }
}
