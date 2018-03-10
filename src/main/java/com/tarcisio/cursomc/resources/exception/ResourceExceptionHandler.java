package com.tarcisio.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tarcisio.cursomc.services.exceptions.DataIntegrityException;
import com.tarcisio.cursomc.services.exceptions.ObjectNotFoundException;
import com.tarcisio.cursomc.services.exceptions.ValidationError;

@ControllerAdvice
public class ResourceExceptionHandler {
	/*
	 * Essa classe intercepta os erros que virão na tela, nas requisições do json
	 */
	@ExceptionHandler(ObjectNotFoundException.class) // indica que é um tratador de excesões de da classe
														// objectNtFoundExceptio
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		/*
		 * esse retorno aqui será exibido no formato jsom a proria API se encarregará de
		 * exibir na tela da forma correta, exibindo chave valor da classe StandardErro
		 * (STATUS,MSG e TIMESTAMP)
		 */
	}

	@ExceptionHandler(DataIntegrityException.class) // indica que é um tratador de excesões de da classe
	// objectNtFoundExceptio
	public ResponseEntity<StandardError> dataIntegrity(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);// esse retorno aqui será exibido no formato jsom a
		// proria API se encarregará de exibir na tela
		// da forma correta, exibindo chave valor da
		// classe StandardErro (STATUS,MSG e TIMESTAMP)
		
		//isso aqui é quando houver um bad_request em qualquer momento na aplicação
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) // indica que é um tratador de excesões de da classe
	// objectNtFoundExceptio
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação", System.currentTimeMillis());
		//e.getBindingResult().getFieldErrors(); esse metodo retorna todos os erros que o sistema capturou
		for(FieldError x:e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);// esse retorno aqui será exibido no formato jsom a
		
	}
	
	
}
