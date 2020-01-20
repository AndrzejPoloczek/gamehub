package gamehub.usermanager.dto;

import javax.validation.constraints.Size;

public class UserUpdateDTO {

	@Size(min = 5, max = 16)
	private String displayName;
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
