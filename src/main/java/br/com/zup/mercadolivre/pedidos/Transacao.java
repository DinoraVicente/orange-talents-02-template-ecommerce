package br.com.zup.mercadolivre.pedidos;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private StatusTransacao status;
    @NotBlank
    private String idTransacao;
    @ManyToOne
    private Compra compra;
    @NotNull
    private LocalDateTime instante;

    @Deprecated
    public Transacao() {
    }

    public Transacao(@NotNull StatusTransacao status, @NotBlank String idTransacao, @NotNull @Valid Compra compra) {
        this.status = status;
        this.idTransacao = idTransacao;
        this.compra = compra;
        this.instante = LocalDateTime.now();
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Compra getCompra() {
        return compra;
    }

    public boolean concluidaComSucesso(){
        return this.status.equals(StatusTransacao.sucesso);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idTransacao == null) ? 0 : idTransacao.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transacao other = (Transacao) obj;
        if (idTransacao == null) {
            if (other.idTransacao != null)
                return false;
        } else if (!idTransacao.equals(other.idTransacao))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", status=" + status +
                ", idTransacao='" + idTransacao + '\'' +
                ", instante=" + instante +
                '}';
    }
}
