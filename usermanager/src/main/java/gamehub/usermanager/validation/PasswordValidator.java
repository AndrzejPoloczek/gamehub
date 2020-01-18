package gamehub.usermanager.validation;

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
			
		if(!(user.getPassword().equals(user.getPasswordRepeat()))){
			errors.rejectValue("password", "notmatch.password");
		}
	}

}
