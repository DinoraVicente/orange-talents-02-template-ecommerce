package br.com.zup.mercadolivre.produtos.perguntas;

import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/produto")
public class PerguntasController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping(value = "/{id}/perguntas")
    @Transactional
    public String cadastraPerguntas(@PathVariable("id") Long id,@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody PerguntasRequest request){
        Usuario dono = usuario.get();
        Produto produto = manager.find(Produto.class, id);

        Perguntas perguntas = request.toModel(dono, produto);
        manager.persist(perguntas);

        return perguntas.toString();
    }
}