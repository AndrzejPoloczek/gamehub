package gamehub.sdk.dto.gamebind;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "Game join data")
public class GameJoinDTO extends PlayerDTO {

	@ApiModelProperty(required = true, name = "Game bind guid", notes = "Unique guid of game bind")
	@NotBlank
	private String guid;


	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
