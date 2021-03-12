package br.com.zup.mercadolivre.categorias;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nome;

    @ManyToOne
    private Categoria categoriaMae;

    @Deprecated
    public Categoria() {
    }

    public Categoria(@NotBlank String nome) {
        this.nome = nome;
    }

    public Categoria(@NotBlank String nome, Categoria categoriaMae) {
        this.nome = nome;
        this.categoriaMae = categoriaMae;
    }

    public String getNome() {
        return nome;
    }
}