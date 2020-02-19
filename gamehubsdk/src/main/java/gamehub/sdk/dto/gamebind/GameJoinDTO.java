package gamehub.sdk.dto.gamebind;

import javax.validation.constraints.NotBlank;

public class GameJoinDTO extends PlayerDTO {
	
	@NotBlank
	private String guid;


	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
