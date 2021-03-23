package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.pedidos.pagseguro.RetornoPagSeguroRequest;
import br.com.zup.mercadolivre.usuarios.Usuario;
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

    @PostMapping("/retorno-pagseguro/{id}")
    @Transactional
    public String pagamentoPagSeguro(@AuthenticationPrincipal Usuario usuario,
                                     @PathVariable("id") Long idCompra,
                                     @RequestBody @Valid RetornoPagSeguroRequest request){
        Compra compra = manager.find(Compra.class, idCompra);
        compra.addTransacao(request);
        manager.merge(compra);

        return compra.toString();
    }
}