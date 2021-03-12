package br.com.zup.mercadolivre.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/cadastro")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioRequest> cadastroUsuario(@RequestBody @Valid UsuarioRequest request){
        Usuario usuario = request.toModel();
        repository.save(usuario);

        return ResponseEntity.ok().build();
    }
}