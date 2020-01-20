package gamehub.usermanager.validation;

import java.util.Objects;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import gamehub.usermanager.dto.UserCreateDTO;

public class PasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserCreateDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserCreateDTO user = (UserCreateDTO)target;
		
		if (Objects.isNull(user.getPassword())) {
			errors.rejectValue("password", "null.password");
		} else if (!(user.getPassword().equals(user.getPasswordRepeat()))){
			errors.rejectValue("passwordRepeat", "notmatch.password");
		}
	}

}
