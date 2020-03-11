package gamehub.sdk.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User information")
public class UserInfoDTO {

	@ApiModelProperty(required = true, name = "Display name", notes = "User's display name")
	private String displayName;

	@ApiModelProperty(required = true, name = "Username", notes = "User's username")
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
