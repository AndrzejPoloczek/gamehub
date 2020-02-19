package gamehub.gamebind.model;

import java.util.List;

public class GameBind {

    private String guid;
    private GameType type;
    private Player owner;
    private int expectedPlayers;
    private GameBindStatus status;
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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getExpectedPlayers() {
        return expectedPlayers;
    }

    public void setExpectedPlayers(int expectedPlayers) {
        this.expectedPlayers = expectedPlayers;
    }

    public GameBindStatus getStatus() {
        return status;
    }

    public void setStatus(GameBindStatus status) {
        this.status = status;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
