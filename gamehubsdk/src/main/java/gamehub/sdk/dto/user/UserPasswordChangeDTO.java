package gamehub.sdk.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import gamehub.sdk.validation.PasswordConfirmation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User password change data")
@PasswordConfirmation(passwordConfirmField = "passwordRepeat")
public class UserPasswordChangeDTO {

	@ApiModelProperty(required = true, name = "Password", notes = "Users' password")
	@NotBlank
	@Size(min = 5, max = 16)
	private String password;

	@ApiModelProperty(required = true, name = "Password confirmation", notes = "Users' password confirmation")
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
