package gamehub.ox3.controller;

import gamehub.ox3.exception.GamePlayException;
import gamehub.sdk.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GamePlayException.class)
    public ResponseEntity<ApiError> handleGameBindException(GamePlayException ex) {
        return ResponseEntity.badRequest().body(ApiError.buildFromException(HttpStatus.BAD_REQUEST, ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ApiError.buildFromException(HttpStatus.BAD_REQUEST, ex));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ApiError.buildFromException(HttpStatus.BAD_REQUEST, ex));
    }
}
