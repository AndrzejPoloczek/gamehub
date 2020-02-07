package gamehub.gamebind.dto;

import java.util.List;

import gamehub.gamebind.model.GameType;

public class GameBindDTO {

	private GameType type;
	private String guid;
	private String owner;
	private List<String> players;
	
	
	public GameType getType() {
		return type;
	}

	public void setType(GameType type) {
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
