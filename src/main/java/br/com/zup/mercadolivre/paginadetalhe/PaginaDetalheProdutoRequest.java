package br.com.zup.mercadolivre.paginadetalhe;

import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.produtos.caracteristicas.Caracteristica;
import br.com.zup.mercadolivre.produtos.opniao.Opiniao;
import br.com.zup.mercadolivre.produtos.opniao.Opinioes;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

public class PaginaDetalheProdutoRequest {

    @NotBlank
    private String nomeProduto;
    private String descricao;
    @NotNull
    private BigDecimal preco;
    @NotNull
    @Valid
    private Collection<Caracteristica> caracteristicas = new ArrayList<>();
    private Set<String> linksImagens;
    private Set<String> perguntas;
    private Set<Map<String, String>> opinioes;
    private double mediaNotas;
    private int totalOpinioes;

    public PaginaDetalheProdutoRequest(Produto produto) {
        this.nomeProduto = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getValor();
        this.caracteristicas = produto.getCaracteristicas();
        this.linksImagens = produto.mapeiaImagens(imagens -> imagens.getLink());
        this.perguntas = produto.mapeiaPerguntas(pergunta -> pergunta.getTitulo());
        Opinioes opinioes = getOpinioes(produto);
        this.mediaNotas = opinioes.media();
        this.totalOpinioes = opinioes.total();
    }

    private Opinioes getOpinioes(Produto produto) {
        Opinioes opinioes = produto.getOpinioes();
        this.opinioes = opinioes.mapeiaOpinioes(opniao -> {
            return Map.of("titulo", opniao.getTitulo(), "descricao", opniao.getDescricao()); });
        return opinioes;
    }

    public double getMediaNotas() {
        return mediaNotas;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Collection<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<String> getLinksImagens() {
        return linksImagens;
    }

    public Set<String> getPerguntas() {
        return perguntas;
    }

    public Set<Map<String, String>> getOpinioes() {
        return opinioes;
    }

    public int getTotalOpinioes() {
        return totalOpinioes;
    }
}