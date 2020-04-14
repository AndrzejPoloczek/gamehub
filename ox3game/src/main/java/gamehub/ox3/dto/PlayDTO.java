package gamehub.ox3.dto;

import gamehub.ox3.model.Figure;

public class PlayDTO {

    private boolean canMove;
    private Figure[][] area;
    private boolean isOver;
    private boolean isWinner;
    private String winnerCoordinates;

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public Figure[][] getArea() {
        return area;
    }

    public void setArea(Figure[][] area) {
        this.area = area;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public String getWinnerCoordinates() {
        return winnerCoordinates;
    }

    public void setWinnerCoordinates(String winnerCoordinates) {
        this.winnerCoordinates = winnerCoordinates;
    }
}
