package gamehub.sdk.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gamehub.sdk.dto.user.UserPasswordChangeDTO;

public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, UserPasswordChangeDTO> {

	private String field;
	private String message;
	
	@Override
	public void initialize(PasswordConfirmation annotation) {
		this.field = annotation.passwordConfirmField();
		this.message = annotation.message();
	}
	
	@Override
	public boolean isValid(UserPasswordChangeDTO value, ConstraintValidatorContext context) {
		if (!value.getPassword().equals(value.getPasswordRepeat())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(field)
					.addConstraintViolation();
			return false;
		} else {
			return true;
		}
	}
}
