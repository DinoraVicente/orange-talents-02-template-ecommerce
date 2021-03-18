package br.com.zup.mercadolivre.produtos.perguntas;

import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.usuarios.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Perguntas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    private LocalDateTime instante = LocalDateTime.now();
    @NotNull
    @Valid
    @ManyToOne
    private Usuario questionador;
    @NotNull
    @Valid
    @ManyToOne
    private Produto produto;

    @Deprecated
    public Perguntas() {
    }

    public Perguntas(@NotBlank String titulo, @NotNull @Valid Usuario questionador, @NotNull @Valid Produto produto) {
        this.titulo = titulo;
        this.questionador = questionador;
        this.produto = produto;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Usuario getQuestionador() {
        return questionador;
    }

    public Usuario getUsuario() {
        return questionador;
    }

    public Usuario getDonoProduto() {
        return produto.getDono();
    }
}
