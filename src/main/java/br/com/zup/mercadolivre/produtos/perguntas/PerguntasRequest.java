package br.com.zup.mercadolivre.produtos.perguntas;

import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.usuarios.Usuario;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PerguntasRequest {

    @NotBlank
    private String titulo;

    @Deprecated
    public PerguntasRequest() {
    }

    public PerguntasRequest(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public Perguntas toModel(@NotNull @Valid Usuario usuario, @NotNull @Valid Produto produto) {
        return new Perguntas(titulo, usuario, produto);
    }
}