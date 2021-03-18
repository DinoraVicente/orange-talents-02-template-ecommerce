package br.com.zup.mercadolivre.produtos.opniao;

import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpniaoRequest {

    @Min(1) @Max(5)
    private Integer nota;
    @NotBlank
    private String titulo;
    @NotBlank
    @Length(max = 500)
    private String descricao;

    @Deprecated
    public OpniaoRequest() {
    }

    public OpniaoRequest(@Min(1) @Max(5) Integer nota, @NotBlank String titulo,
                         @NotBlank @Length(max = 500) String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Opniao toModel(@NotNull @Valid Usuario usuario, @NotNull @Valid Produto produto) {
        return new Opniao(nota, titulo, descricao, usuario, produto);
    }
}