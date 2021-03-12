package br.com.zup.mercadolivre.categorias;

import br.com.zup.mercadolivre.config.validacoes.UnicoValor;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CategoriaRequest {

    @NotBlank
    @UnicoValor(domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    @Positive
    private Long idCategoriaMae;

    public String getNome() {
        return nome;
    }

    public Long getIdCategoriaMae() {
        return idCategoriaMae;
    }

    public Categoria toModel(EntityManager manager) {
        Categoria categoria = new Categoria(nome);
        if (idCategoriaMae != null) {
            Categoria categoriaMae = manager.find(Categoria.class, idCategoriaMae);
            Assert.notNull(categoriaMae, "Id nao pode ser nulo");
            categoria = new Categoria(nome, categoriaMae);
        }
        return categoria;
    }
}