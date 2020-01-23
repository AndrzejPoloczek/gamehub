package gamehub.usermanager.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConfirmationValidator.class)
public @interface PasswordConfirmation {
	String passwordConfirmField();
	String message() default "Passwords must be the same";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}