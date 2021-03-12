package br.com.zup.mercadolivre.config.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HandlerValidator {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorRequestDto> handler(MethodArgumentNotValidException exception) {

		List<ErrorRequestDto> dto = new ArrayList<ErrorRequestDto>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		fieldErrors.forEach(e ->{
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorRequestDto erro = new ErrorRequestDto(e.getField(), mensagem);
			dto.add(erro);
		});

		return dto;
	}
}