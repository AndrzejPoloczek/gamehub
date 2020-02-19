package gamehub.sdk.dto.gamebind;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class GameCreateDTO extends PlayerDTO {


	@NotNull
	private String type;
	@Min(1)
	private int players;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}
}
