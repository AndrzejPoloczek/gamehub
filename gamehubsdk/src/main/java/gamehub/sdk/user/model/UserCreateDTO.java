package gamehub.sdk.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserCreateDTO extends UserPasswordChangeDTO {

	@Size(min = 5, max = 16)
	private String displayName;
	
	@NotBlank
	@Size(min = 5, max = 16)
	private String username;
	
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}