package com.tarcisio.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tarcisio.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	/*
	 * Essa classe intercepta os erros que virão na tela, nas requisições do json
	 */
	@ExceptionHandler(ObjectNotFoundException.class) // indica que é um tratador de excesões de da classe
														// objectNtFoundExceptio
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);// esse retorno aqui será exibido no formato jsom a
																		// proria API se encarregará de exibir na tela
																		// da forma correta, exibindo chave valor da
																		// classe StandardErro (STATUS,MSG e TIMESTAMP)
	}
}
