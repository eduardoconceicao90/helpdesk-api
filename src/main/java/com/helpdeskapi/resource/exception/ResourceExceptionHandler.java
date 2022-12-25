package com.helpdeskapi.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.helpdeskapi.service.exception.DataIntegratyViolationException;
import com.helpdeskapi.service.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request){
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), 
				"Object Not Found Exception", e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandardError> dataIntegratyViolationException(DataIntegratyViolationException e, HttpServletRequest request){
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
				"Data Integraty Violation Exception", e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError error = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
				"Validation Errors", "Erro na validação dos campos", request.getRequestURI());

		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			error.addErrors(x.getField(), x.getDefaultMessage());
		}	

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
