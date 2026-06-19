package com.ecommerce.computerequipment.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
	
	private HttpStatus status;
	private String error;
	private String message;
}
