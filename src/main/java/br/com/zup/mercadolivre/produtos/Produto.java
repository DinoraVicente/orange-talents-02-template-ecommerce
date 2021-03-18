package br.com.zup.mercadolivre.produtos;

import br.com.zup.mercadolivre.categorias.Categoria;
import br.com.zup.mercadolivre.produtos.caracteristicas.Caracteristica;
import br.com.zup.mercadolivre.produtos.caracteristicas.CaracteristicaRequest;
import br.com.zup.mercadolivre.produtos.imagens.ImagensProduto;
import br.com.zup.mercadolivre.produtos.opniao.Opniao;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    @Positive
    private Integer quantidade;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Collection<Caracteristica> caracteristicas = new ArrayList<>();
    @NotBlank
    @Length(max = 1000)
    private String descricao;
    @NotNull
    @Valid
    @ManyToOne
    private Categoria categoria;
    private LocalDate instanteCriacao = LocalDate.now();
    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagensProduto> imagens = new HashSet<>();
    @NotNull
    @Valid
    @ManyToOne
    private Usuario dono;

    @Deprecated
    public Produto() {
    }

    public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal valor,
                   @NotNull @Positive Integer quantidade,
                   @Size(min = 3) @Valid Collection<CaracteristicaRequest> caracteristicas,
                   @NotBlank @Max(1000) String descricao, @NotNull Categoria categoria,
                   LocalDate instanteCriacao, @NotNull @Valid Usuario dono) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas.addAll(caracteristicas.stream()
                .map(caracteristica -> caracteristica.toModel(this))
                .collect(Collectors.toSet()));
        this.descricao = descricao;
        this.categoria = categoria;
        this.instanteCriacao = instanteCriacao;
        this.dono = dono.get();

        Assert.isTrue(this.caracteristicas.size() >= 3,
                "Todo produto precisa ter no mínimo 3 ou mais características");
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Collection<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDate getInstanteCriacao() {
        return instanteCriacao;
    }

    public Long getId() {
        return id;
    }

    public Usuario getDono() {
        return dono;
    }

    public void associaImagem(Set<String> links) {
        links.stream().map(link ->
                new ImagensProduto(this, link)).collect(Collectors.toSet());
        this.imagens.addAll(imagens);
    }

    public boolean pertenceAoUsuario(Usuario usuario) {
        return this.dono.equals(usuario);
    }
}