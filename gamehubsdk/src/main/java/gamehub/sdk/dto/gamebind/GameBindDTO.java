package gamehub.sdk.dto.gamebind;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Game bind information")
public class GameBindDTO {

	@ApiModelProperty(required = true, name = "Game type", notes = "Chosen game type")
	private String type;

	@ApiModelProperty(required = true, name = "Game bind guid", notes = "Unique guid of game bind")
	private String guid;

	@ApiModelProperty(required = true, name = "Owner", notes = "Game owner's name")
	private String owner;

	@ApiModelProperty(required = true, name = "Current players", notes = "Names of all players, who already joined game")
	private List<String> players;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getGuid() {
		return guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}
}
