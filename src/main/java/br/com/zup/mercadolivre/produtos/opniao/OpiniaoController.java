package br.com.zup.mercadolivre.produtos.opniao;

import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/produto")
public class OpiniaoController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping(value = "/{id}/opniao")
    @Transactional
    public ResponseEntity<?> cadastraOpniao(@AuthenticationPrincipal Usuario usuario,
                                         @PathVariable("id") Long id, @RequestBody @Valid OpiniaoRequest request){

        Usuario dono = usuario.get();
        Produto produto = manager.find(Produto.class, id);
        Opiniao opniao = request.toModel(dono, produto);

        manager.persist(opniao);

        return ResponseEntity.ok().build();
    }
}
