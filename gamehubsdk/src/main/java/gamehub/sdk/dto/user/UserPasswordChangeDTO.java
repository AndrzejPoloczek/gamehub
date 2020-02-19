package gamehub.sdk.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import gamehub.sdk.validation.PasswordConfirmation;

@PasswordConfirmation(passwordConfirmField = "passwordRepeat")
public class UserPasswordChangeDTO {
	
	@NotBlank
	@Size(min = 5, max = 16)
	private String password;
	
	@NotBlank
	@Size(min = 5, max = 16)
	private String passwordRepeat;

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}
}
