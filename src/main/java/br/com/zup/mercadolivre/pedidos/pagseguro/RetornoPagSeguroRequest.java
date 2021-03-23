package br.com.zup.mercadolivre.pedidos.pagseguro;

import br.com.zup.mercadolivre.pedidos.Compra;
import br.com.zup.mercadolivre.pedidos.Transacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RetornoPagSeguroRequest {

    @NotBlank
    private String idTransacao;
    @NotNull
    private StatusRetornoPagseguro status;

    @Deprecated
    public RetornoPagSeguroRequest() {
    }

    public RetornoPagSeguroRequest(@NotBlank String idTransacao, @NotNull StatusRetornoPagseguro status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public StatusRetornoPagseguro getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RetornoPagSeguroRequest{" +
                "idTransacao='" + idTransacao + '\'' +
                ", status=" + status +
                '}';
    }

    public Transacao toTransacao(Compra compra) {
        return new Transacao(status.normaliza(),idTransacao, compra);
    }
}
