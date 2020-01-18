package gamehub.usermanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserPasswordChange {
	
	@NotBlank
	@Size(min = 5, max = 16)
	private String password;
	
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
