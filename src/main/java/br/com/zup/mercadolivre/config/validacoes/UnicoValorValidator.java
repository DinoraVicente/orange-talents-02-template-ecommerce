package br.com.zup.mercadolivre.config.validacoes;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UnicoValorValidator implements ConstraintValidator<UnicoValor, Object> {

    @PersistenceContext
    private EntityManager manager;

    private Class<?> klass;
    private String domainAttribute;

    @Override
    public void initialize(UnicoValor params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("select 1 from "+ klass.getName() +" where "+ domainAttribute +"= :value").setParameter("value", value);

        List<?> list = query.getResultList();
        Assert.state(list.size() <=1, "Foi encontrado mais de uma "+ klass +" com o atributo "+ domainAttribute +" = " + value);

        return list.isEmpty();
    }
}