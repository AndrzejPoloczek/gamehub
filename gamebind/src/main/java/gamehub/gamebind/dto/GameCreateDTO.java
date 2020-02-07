package gamehub.gamebind.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import gamehub.gamebind.model.GameType;
import gamehub.gamebind.validation.GameCreation;

@GameCreation
public class GameCreateDTO {

	@NotBlank
	private String username;
	
	@NotNull
	private GameType type;
	
	@Min(1)
	private int players;
	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public GameType getType() {
		return type;
	}

	public void setType(GameType type) {
		this.type = type;
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}
	
	
}
