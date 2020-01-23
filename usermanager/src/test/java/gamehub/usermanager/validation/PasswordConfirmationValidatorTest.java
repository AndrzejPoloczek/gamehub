package gamehub.usermanager.validation;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gamehub.usermanager.dto.UserPasswordChangeDTO;

@RunWith(MockitoJUnitRunner.class)
public class PasswordConfirmationValidatorTest {

	@InjectMocks
	private PasswordConfirmationValidator testObj;
	
	@Mock
	private UserPasswordChangeDTO value;
	@Mock
	private ConstraintValidatorContext context;
	@Mock
	private ConstraintViolationBuilder builder;
	@Mock
	private NodeBuilderCustomizableContext nodeBuilder;
	@Mock
	private PasswordConfirmation passwordConfirmation;

	@Before
	public void setUp() {
		when(passwordConfirmation.message()).thenReturn("message");
		when(passwordConfirmation.passwordConfirmField()).thenReturn("passwordConfirmField");
		when(context.buildConstraintViolationWithTemplate("message")).thenReturn(builder);
		when(builder.addPropertyNode("passwordConfirmField")).thenReturn(nodeBuilder);
		testObj.initialize(passwordConfirmation);
	}
	
	@Test
	public void shouldValidateWithSuccess() {
		
		// given
		when(value.getPassword()).thenReturn("pass");
		when(value.getPasswordRepeat()).thenReturn("pass");
		
		// when
		final boolean result = testObj.isValid(value, context);
		
		// then
		Assertions.assertThat(result).isTrue();
		verify(context, never()).disableDefaultConstraintViolation();
		verify(context, never()).buildConstraintViolationWithTemplate("message");
	}

	@Test
	public void shouldValidateWithError() {
		
		// given
		when(value.getPassword()).thenReturn("pass");
		when(value.getPasswordRepeat()).thenReturn("otherpass");
		
		// when
		final boolean result = testObj.isValid(value, context);
		
		// then
		Assertions.assertThat(result).isFalse();
		verify(context).disableDefaultConstraintViolation();
		verify(context).buildConstraintViolationWithTemplate("message");
		verify(builder).addPropertyNode("passwordConfirmField");
		verify(nodeBuilder).addConstraintViolation();
	}
	
}
