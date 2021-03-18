package br.com.zup.mercadolivre.produtos.caracteristicas;

import br.com.zup.mercadolivre.produtos.ProdutoRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class CaracteristicaNomeIgualValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProdutoRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()){
            return;
        }

        ProdutoRequest request = (ProdutoRequest) target;
        Set<String> nomesIguais = request.buscaPorCaracteristicasIguais();
        if(!nomesIguais.isEmpty()) {
            errors.rejectValue("caracteristicas", null,
                    "Não pode ter caracteristicas iguais");
        }
    }
}