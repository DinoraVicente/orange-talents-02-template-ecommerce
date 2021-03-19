package br.com.zup.mercadolivre.paginadetalhe;

import br.com.zup.mercadolivre.produtos.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RestController
@RequestMapping("/api/paginadetalhe")
public class PaginaDetalheController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping("/{id}")
    public PaginaDetalheProdutoRequest listaDetalhes(@PathVariable("id") Long id) {
        Produto produto = Optional.ofNullable(manager.find(Produto.class, id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new PaginaDetalheProdutoRequest(produto);
    }
}