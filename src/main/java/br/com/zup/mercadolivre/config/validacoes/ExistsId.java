package br.com.zup.mercadolivre.config.validacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD) 
@Constraint(validatedBy = {ExistsIdValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsId {
	String message() default "Id já existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String fieldName();
    Class<?> domainClass();
}