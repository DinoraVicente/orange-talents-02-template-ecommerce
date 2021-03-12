package br.com.zup.mercadolivre.usuarios;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotBlank
    @Column(unique = true)
    private String login;
    @NotBlank
    @Size(min = 6)
    private String senha;
    private LocalDate instanteCriacao = LocalDate.now();

    @Deprecated
    public Usuario() {
    }

    public Usuario(@Email @NotBlank String login,
                   @Valid @NotNull SenhaLimpa senhaLimpa) {
        Assert.isTrue(StringUtils.hasLength(login),"login não pode estar em branco");
        Assert.notNull(senhaLimpa,"senha limpa não pode ser nulo");

        this.login = login;
        this.senha = senhaLimpa.hash();
    }

    public Usuario(@Email @NotBlank String login,
                   @Valid @NotNull SenhaLimpa senhaLimpa,
                   @NotNull LocalDate instanteCriacao) {
        Assert.isTrue(StringUtils.hasLength(login),"login não pode estar em branco");
        Assert.notNull(senhaLimpa,"senha limpa não pode ser nulo");

        this.login = login;
        this.senha = senhaLimpa.hash();
        this.instanteCriacao = instanteCriacao;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getInstante() {
        return instanteCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(login, usuario.login) && Objects.equals(senha, usuario.senha) && Objects.equals(instanteCriacao, usuario.instanteCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, senha, instanteCriacao);
    }
}