package gamehub.gameplay.model;

import gamehub.sdk.enums.GameType;

import java.util.List;

public class GamePlay {

    private String guid;
    private GameType type;
    private List<Player> players;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
