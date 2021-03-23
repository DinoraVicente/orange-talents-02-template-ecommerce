package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.config.email.Emails;
import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class EfetuaCompraController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Emails emails;

    @PostMapping("/compras")
    @Transactional
    public String efetuaCompra(@RequestBody @Valid CompraRequest request,
                               @AuthenticationPrincipal Usuario usuario,
                               UriComponentsBuilder uriComponentsBuilder) throws BindException {
        Produto produto = manager.find(Produto.class, request.getIdProduto());
        if (produto == null) throw new IllegalArgumentException("Id do produto não existe!");

        int quantidade = request.getQuantidade();
        boolean abateEstoque = produto.abateEstoque(quantidade);

        if(abateEstoque){
            Usuario comprador = usuario.get();
            GatewayPagamento gateway = request.getGateway();
            Compra compra = new Compra(produto, quantidade, comprador, gateway);
            manager.persist(compra);

            emails.novaCompra(compra);

            return compra.retornoUrl(request, uriComponentsBuilder, compra);
        }
        BindException estoqueErro = new BindException(request, "compraRequest");
        estoqueErro.reject("Não foi possível realizar a compra por conta do estoque");
        throw new BindException(estoqueErro);
    }
}