package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.pedidos.pagseguro.RetornoPagSeguroRequest;
import br.com.zup.mercadolivre.pedidos.paypal.RetornoPaypalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class EfetuaCompraPt2Controller {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private EventosNovaCompra eventosNovaCompra;

    @PostMapping(value = "/retorno-pagseguro/{id}")
    @Transactional
    public String pagamentoPagSeguro(@AuthenticationPrincipal UserDetails usuario,
                                     @PathVariable("id") Long idCompra,
                                     @RequestBody @Valid RetornoPagSeguroRequest request){
        return processa(idCompra, request);
    }

    @PostMapping(value = "/retorno-paypal/{id}")
    @Transactional
    public String pagamentoPaypal(@AuthenticationPrincipal UserDetails usuario,
                                  @PathVariable("id") Long idCompra,
                                  @RequestBody @Valid RetornoPaypalRequest request){
        return processa(idCompra, request);
    }

    private String processa(Long idCompra, RetornoGatewayPagamento retornoGatewayPagamento){
        Compra compra = manager.find(Compra.class, idCompra);
        compra.addTransacao(retornoGatewayPagamento);
        manager.merge(compra);

        eventosNovaCompra.processa(compra);

        return compra.toString();
    }
}