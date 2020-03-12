package gamehub.sdk.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

@ApiModel(description = "User update data")
public class UserUpdateDTO {

	@ApiModelProperty(required = true, name = "Display name", notes = "User's display name")
	@Size(min = 5, max = 16)
	private String displayName;
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
