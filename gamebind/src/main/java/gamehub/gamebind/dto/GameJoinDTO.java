package gamehub.gamebind.dto;

import javax.validation.constraints.NotBlank;

public class GameJoinDTO {

	@NotBlank
	private String username;
	
	@NotBlank
	private String guid;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
