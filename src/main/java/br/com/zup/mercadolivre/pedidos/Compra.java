package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.pedidos.pagseguro.RetornoPagSeguroRequest;
import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive
    private Integer quantidade;
    @ManyToOne
    @NotNull
    @Valid
    private Usuario comprador;
    @ManyToOne
    @NotNull
    @Valid
    private Produto produto;
    @Enumerated
    @NotNull
    private GatewayPagamento gateway;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

    @Deprecated
    public Compra() {
    }

    public Compra(@NotNull @Valid Produto produto, @Positive Integer quantidade,
                  @NotNull @Valid Usuario comprador, @NotNull GatewayPagamento gateway) {
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.produto = produto;
        this.gateway = gateway;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Produto getProduto() {
        return produto;
    }

    public GatewayPagamento getGateway() {
        return gateway;
    }

    public String retornoUrl(CompraRequest request, UriComponentsBuilder uriComponentsBuilder, Compra compra) {
        if(request.getGateway().equals(GatewayPagamento.PAGSEGURO)) {
            String urlPagseguro = uriComponentsBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(compra.getId()).toString();
            return "pagseguro.com/" + compra.getId() + "?redirectUrl=" + urlPagseguro;
        }else {
            String urlPaypal = uriComponentsBuilder.path("/retorno-paypal/{id}").buildAndExpand(compra.getId()).toString();
            return "paypal.com/" + compra.getId() + "?redirectUrl=" + urlPaypal;
        }
    }

    public void addTransacao(@Valid RetornoPagSeguroRequest request) {
        Transacao novaTransacao = request.toTransacao(this);
        Assert.isTrue(!this.transacoes.contains(novaTransacao), "Já existe uma transação igual a essa");
        Set<Transacao> transacoesConcluidas = this.transacoes.stream()
                                            .filter(Transacao::concluidaComSucesso)
                                            .collect(Collectors.toSet());
        Assert.isTrue(transacoesConcluidas.isEmpty(), "Essa compra já foi concluída com sucesso!");
        this.transacoes.add(novaTransacao);
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", comprador=" + comprador +
                ", produto=" + produto +
                ", gateway=" + gateway +
                ", transacoes=" + transacoes +
                '}';
    }
}
