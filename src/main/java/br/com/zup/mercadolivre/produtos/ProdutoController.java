package br.com.zup.mercadolivre.produtos;

import br.com.zup.mercadolivre.produtos.caracteristicas.CaracteristicaNomeIgualValidator;
import br.com.zup.mercadolivre.produtos.imagens.ImagensRequest;
import br.com.zup.mercadolivre.produtos.imagens.UploadFile;
import br.com.zup.mercadolivre.produtos.opniao.Opniao;
import br.com.zup.mercadolivre.produtos.opniao.OpniaoRequest;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {
    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UploadFile uploadFile;

    @InitBinder(value = "novoProdutoRequest")
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new CaracteristicaNomeIgualValidator());
    }

    @PostMapping(value = "/cadastro")
    @Transactional
    public ResponseEntity<?> cadastraProduto(@AuthenticationPrincipal Usuario dono,@RequestBody @Valid ProdutoRequest request){
        Produto produto = request.toModel(manager, dono);
        manager.persist(produto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/imagens")
    @Transactional
    public ResponseEntity<?> addImagemDoProduto(@AuthenticationPrincipal Usuario usuario, @PathVariable("id") Long id, @Valid ImagensRequest request){
        Set<String> links = uploadFile.envia(request.getImagens());
        Produto produto = manager.find(Produto.class, id);
        Usuario dono = usuario.get();

        if(!produto.pertenceAoUsuario(dono)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        produto.associaImagem(links);
        manager.merge(produto);

        return ResponseEntity.ok().build();
    }
}