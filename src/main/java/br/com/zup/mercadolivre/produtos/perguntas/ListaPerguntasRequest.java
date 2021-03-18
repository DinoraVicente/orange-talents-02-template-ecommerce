package br.com.zup.mercadolivre.produtos.perguntas;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ListaPerguntasRequest {

    @NotBlank
    private String titulo;
    private LocalDateTime instante;
    private Long questionador;

    @Deprecated
    public ListaPerguntasRequest() {
    }

    public ListaPerguntasRequest(Perguntas perguntas) {
        this.titulo = perguntas.getTitulo();
        this.questionador = perguntas.getQuestionador().getId();
        this.instante = perguntas.getInstante();
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Long getQuestionador() {
        return questionador;
    }
}