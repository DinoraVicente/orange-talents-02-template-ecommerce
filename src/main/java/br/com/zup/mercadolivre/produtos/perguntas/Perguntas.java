package br.com.zup.mercadolivre.produtos.perguntas;

import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.produtos.caracteristicas.Caracteristica;
import br.com.zup.mercadolivre.usuarios.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Perguntas implements Comparable<Perguntas>{

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Perguntas other = (Perguntas) obj;
        if (questionador == null) {
            if (other.questionador != null)
                return false;
        } else if (!questionador.equals(other.questionador))
            return false;
        if (produto == null) {
            if (other.produto != null)
                return false;
        } else if (!produto.equals(other.produto))
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = prime * result + ((produto == null) ? 0 : produto.hashCode());
        return result;
    }

    @Override
    public int compareTo(Perguntas o) {
        return this.titulo.compareTo(o.titulo);
    }
}