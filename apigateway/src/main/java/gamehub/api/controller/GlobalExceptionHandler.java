package gamehub.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import gamehub.sdk.error.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HystrixBadRequestException.class) 
	public ResponseEntity<ApiError> handleUserNotFound(HystrixBadRequestException ex) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return ResponseEntity.badRequest().body(mapper.readValue(ex.getMessage(), ApiError.class));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		
	}
}
