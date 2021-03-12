package br.com.zup.mercadolivre.config.validacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnicoValorValidator.class)
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UnicoValor {
    String message() default "{custom.unique.field.validator}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String fieldName();
    Class<?> domainClass();
}
