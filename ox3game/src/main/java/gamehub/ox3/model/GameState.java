package gamehub.ox3.model;

import java.util.Map;

public class GameState {

    private String guid;
    private Map<String, String> players;
    private Map<String, Figure> figures;
    private Figure current;
    private Map<String, OX3State> states;
    private Figure[][] area = new Figure[3][3];

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

    public Map<String, OX3State> getStates() {
        return states;
    }

    public void setStates(Map<String, OX3State> states) {
        this.states = states;
    }

    public Figure[][] getArea() {
        return area;
    }

    public void setArea(Figure[][] area) {
        this.area = area;
    }
}
