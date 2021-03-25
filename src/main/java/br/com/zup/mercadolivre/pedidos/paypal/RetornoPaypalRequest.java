package br.com.zup.mercadolivre.pedidos.paypal;

import br.com.zup.mercadolivre.pedidos.Compra;
import br.com.zup.mercadolivre.pedidos.RetornoGatewayPagamento;
import br.com.zup.mercadolivre.pedidos.StatusTransacao;
import br.com.zup.mercadolivre.pedidos.Transacao;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RetornoPaypalRequest implements RetornoGatewayPagamento {

    @Min(0)
    @Max(1)
    private int status;
    @NotBlank
    private String idTransacao;


    @Deprecated
    public RetornoPaypalRequest() {
    }

    public RetornoPaypalRequest(@NotBlank String idTransacao, @Min(0) @Max(1) @NotNull Integer status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public Transacao toTransacao(Compra compra) {
        StatusTransacao statusCalculado = this.status == 0 ? StatusTransacao.erro : StatusTransacao.sucesso;
        return new Transacao(statusCalculado,idTransacao, compra);
    }
}