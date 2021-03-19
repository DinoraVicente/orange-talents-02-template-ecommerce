package br.com.zup.mercadolivre.produtos.perguntas;

import br.com.zup.mercadolivre.config.email.Emails;
import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/produto")
public class PerguntasController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Emails emails;

    @PostMapping(value = "/{id}/perguntas")
    @Transactional
    public List<ListaPerguntasRequest> cadastraPerguntas(@PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario, @Valid @RequestBody PerguntasRequest request){
        Usuario dono = usuario.get();
        Produto produto = manager.find(Produto.class, id);

        Perguntas perguntas = request.toModel(dono, produto);
        manager.persist(perguntas);

        emails.novaPergunta(perguntas);

        SortedSet<Perguntas> lista = produto.getPerguntas();
        return lista.stream().map(ListaPerguntasRequest::new).collect(Collectors.toList());
    }
}