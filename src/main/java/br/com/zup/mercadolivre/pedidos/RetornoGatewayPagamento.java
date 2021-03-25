package br.com.zup.mercadolivre.pedidos;

public interface RetornoGatewayPagamento {

    /**
     * @param compra
     * @return uma nova transacao dependendo do gateway
     */
    Transacao toTransacao(Compra compra);
}