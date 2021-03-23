package br.com.zup.mercadolivre.pedidos.pagseguro;

import br.com.zup.mercadolivre.pedidos.StatusTransacao;

public enum StatusRetornoPagseguro {
    SUCESSO, ERRO;

    public StatusTransacao normaliza() {
        if(this.equals(SUCESSO)){
            return StatusTransacao.sucesso;
        }
        return StatusTransacao.erro;
    }
}