package gamehub.sdk.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(description = "User create data")
public class UserCreateDTO extends UserPasswordChangeDTO {

	@ApiModelProperty(required = true, name = "Display name", notes = "User's display name")
	@Size(min = 5, max = 16)
	private String displayName;

	@ApiModelProperty(required = true, name = "Username", notes = "User's username")
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
