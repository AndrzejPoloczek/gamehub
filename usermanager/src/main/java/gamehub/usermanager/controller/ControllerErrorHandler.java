package gamehub.usermanager.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import gamehub.sdk.error.ApiError;
import gamehub.usermanager.exception.UserAlreadyExistsException;
import gamehub.usermanager.exception.UserNotFoundException;

@RestControllerAdvice
public class ControllerErrorHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleUserNotFound(UserAlreadyExistsException ex) {
		return ResponseEntity.badRequest().body(ApiError.buildFromException(HttpStatus.BAD_REQUEST, ex));
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
		return ResponseEntity.badRequest().body(ApiError.buildFromException(HttpStatus.BAD_REQUEST, ex));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleUserNotFound(MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest().body(ApiError.buildFromException(HttpStatus.BAD_REQUEST, ex));
	}
}
