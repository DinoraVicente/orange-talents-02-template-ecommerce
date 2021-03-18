package br.com.zup.mercadolivre.usuarios;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotBlank
    @Size(min = 6)
    private String senha;
    private LocalDate instanteCriacao = LocalDate.now();

    @ManyToMany(fetch=FetchType.EAGER)
    private List<Perfil> perfis = new ArrayList<>();

    @Deprecated
    public Usuario() {
    }

    public Usuario(@Email @NotBlank String email,
                   @Valid @NotNull SenhaLimpa senhaLimpa) {
        Assert.isTrue(StringUtils.hasLength(email),"email não pode estar em branco");
        Assert.notNull(senhaLimpa,"senha limpa não pode ser nulo");

        this.email = email;
        this.senha = senhaLimpa.hash();
    }

    public Usuario(@Email @NotBlank String email,
                   @Valid @NotNull SenhaLimpa senhaLimpa,
                   @NotNull LocalDate instanteCriacao) {
        Assert.isTrue(StringUtils.hasLength(email),"email não pode estar em branco");
        Assert.notNull(senhaLimpa,"senha limpa não pode ser nulo");

        this.email = email;
        this.senha = senhaLimpa.hash();
        this.instanteCriacao = instanteCriacao;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
        return Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha) && Objects.equals(instanteCriacao, usuario.instanteCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, senha, instanteCriacao);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuario get() {
        return this;
    }
}