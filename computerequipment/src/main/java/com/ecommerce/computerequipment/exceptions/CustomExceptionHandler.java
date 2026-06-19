package com.ecommerce.computerequipment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecommerce.computerequipment.model.CustomErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ProizvodNotFoundException.class)
	public ResponseEntity<Object> handleProizvodNotFoundException(ProizvodNotFoundException ex, WebRequest request){
		
		CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND, "Proizvod ne postoji!", ex.getMessage());
		return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
	}

}
