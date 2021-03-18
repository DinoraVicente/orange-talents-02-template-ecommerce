package br.com.zup.mercadolivre.usuarios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/cadastro")
public class UsuarioController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioRequest> cadastroUsuario(@RequestBody @Valid UsuarioRequest request){
        Usuario usuario = request.toModel();
        manager.persist(usuario);

        return ResponseEntity.ok().build();
    }
}