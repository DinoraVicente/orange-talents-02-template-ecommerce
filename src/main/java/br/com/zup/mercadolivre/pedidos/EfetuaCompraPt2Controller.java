package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.pedidos.pagseguro.RetornoPagSeguroRequest;
import br.com.zup.mercadolivre.pedidos.paypal.RetornoPaypalRequest;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private NotaFiscal notaFiscal;

    @Autowired
    private Ranking ranking;
//    private EfetuaCompraPt2Controller emailSucesso;

    @PostMapping("/retorno-pagseguro/{id}")
    @Transactional
    public String pagamentoPagSeguro(@AuthenticationPrincipal Usuario usuario,
                                     @PathVariable("id") Long idCompra,
                                     @RequestBody @Valid RetornoPagSeguroRequest request){
        return processa(idCompra, request);
    }

    @PostMapping("/retorno-paypal/{id}")
    @Transactional
    public String pagamentoPaypal(@AuthenticationPrincipal Usuario usuario,
                                  @PathVariable("id") Long idCompra,
                                  @RequestBody @Valid RetornoPaypalRequest request){
        return processa(idCompra, request);
    }

    private String processa(Long idCompra, RetornoGatewayPagamento retornoGatewayPagamento){
        Compra compra = manager.find(Compra.class, idCompra);
        compra.addTransacao(retornoGatewayPagamento);
        manager.merge(compra);

        if(compra.processadaComSucesso()){
            notaFiscal.processa(compra);
            ranking.processa(compra);
//            emailSucesso.processa(compra);
        }
        return compra.toString();
    }
}