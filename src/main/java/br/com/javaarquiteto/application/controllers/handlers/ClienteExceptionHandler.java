package br.com.javaarquiteto.application.controllers.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.javaarquiteto.domain.dtos.ErrorResponseDto;
import br.com.javaarquiteto.domain.exceptions.ClienteException;



@ControllerAdvice
public class ClienteExceptionHandler {
	

	@ExceptionHandler(ClienteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponseDto handleClienteException(ClienteException e) {
		
		List<String> errors = new ArrayList<String>();
		errors.add(e.getMessage());
		
		ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST,errors);		
		
		return response;

	}

}
