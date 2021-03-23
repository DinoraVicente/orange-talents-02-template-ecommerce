package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.config.validacoes.ExistsId;
import br.com.zup.mercadolivre.produtos.Produto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {

    @Positive
    private int quantidade;
    @NotNull
    @ExistsId(fieldName = "id", domainClass = Produto.class)
    private Long idProduto;
    @NotNull
    private GatewayPagamento gateway;

    @Deprecated
    public CompraRequest() {
    }

    public CompraRequest(@Positive int quantidade, @NotNull Long idProduto, @NotNull GatewayPagamento gateway) {
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.gateway = gateway;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public GatewayPagamento getGateway() {
        return gateway;
    }
}