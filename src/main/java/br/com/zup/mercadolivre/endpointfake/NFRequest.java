package br.com.zup.mercadolivre.endpointfake;

import javax.validation.constraints.NotNull;

public class NFRequest {

    @NotNull
    private Long idCompra;
    @NotNull
    private Long idComprador;

    public NFRequest(Long idCompra, Long idComprador) {
        this.idCompra = idCompra;
        this.idComprador = idComprador;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdComprador() {
        return idComprador;
    }

    @Override
    public String toString() {
        return "NFRequest{" +
                "idCompra=" + idCompra +
                ", idComprador=" + idComprador +
                '}';
    }
}
