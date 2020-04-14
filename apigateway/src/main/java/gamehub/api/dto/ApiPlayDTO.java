package gamehub.api.dto;

public class ApiPlayDTO {

    private boolean canMove;
    private String[][] area;
    private boolean isOver;
    private boolean isWinner;
    private String winnerCoordinates;

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public String[][] getArea() {
        return area;
    }

    public void setArea(String[][] area) {
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
