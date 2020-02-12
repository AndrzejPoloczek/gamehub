package gamehub.api.controller;

import com.netflix.client.ClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import gamehub.sdk.error.ApiError;

import java.net.ConnectException;
import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final String MICRO_SERVICE_NOT_AVAILABLE = "Required resources are not available, try again later.";

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

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex) {
		final ApiError error = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage(), Collections.emptySet());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

	@ExceptionHandler({ClientException.class})
	public ResponseEntity<ApiError> handleClientExceptions(ClientException ex) {
		final ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MICRO_SERVICE_NOT_AVAILABLE, Collections.emptySet());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler({ConnectException.class})
	public ResponseEntity<ApiError> handleConnectionExceptions(ConnectException ex) {
		final ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MICRO_SERVICE_NOT_AVAILABLE, Collections.emptySet());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleUserNotFound(MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest().body(ApiError.buildFromException(HttpStatus.BAD_REQUEST, ex));
	}
}
