package gamehub.ox3.model;

import java.util.Map;

public class GameState {

    private String guid;
    private Map<String, String> players;
    private Map<String, Figure> figures;
    private Figure current;
    private OX3State state;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Map<String, String> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, String> players) {
        this.players = players;
    }

    public Map<String, Figure> getFigures() {
        return figures;
    }

    public void setFigures(Map<String, Figure> figures) {
        this.figures = figures;
    }

    public Figure getCurrent() {
        return current;
    }

    public void setCurrent(Figure current) {
        this.current = current;
    }

    public OX3State getState() {
        return state;
    }

    public void setState(OX3State state) {
        this.state = state;
    }
}
