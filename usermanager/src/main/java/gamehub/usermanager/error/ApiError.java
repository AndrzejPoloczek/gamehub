package gamehub.usermanager.error;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ApiError {

	final private HttpStatus status;
	final private String message;
	final private Set<ApiFieldError> errors;
	
	public ApiError(HttpStatus status, String message, Set<ApiFieldError> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}

	public Set<ApiFieldError> getErrors() {
		return errors;
	}
	
	public static final ApiError buildFromException(final HttpStatus status, final Exception ex) {
		return new ApiError(status, ex.getMessage(), Optional.of(ex)
				.filter(MethodArgumentNotValidException.class::isInstance)
				.map(MethodArgumentNotValidException.class::cast)
				.map(ApiError::resolveFieldErrors)
				.orElse(Collections.emptySet()));
	}
	
	private static final Set<ApiFieldError> resolveFieldErrors(final MethodArgumentNotValidException ex) {
		final BindingResult br = ex.getBindingResult();
		if (!br.hasErrors()) {
			return Collections.emptySet();
		}
		return br.getAllErrors()
				.stream()
				.map(error -> new ApiFieldError(resolveFieldName(error), error.getCode(), error.getDefaultMessage()))
				.collect(Collectors.toSet());
	}
	
	private static final String resolveFieldName(final ObjectError error) {
		return Optional.of(error)
				.filter(FieldError.class::isInstance)
				.map(FieldError.class::cast)
				.map(FieldError::getField)
				.orElse("GLOBAL");
	}
}
