package gamehub.ox3.dto;

import gamehub.ox3.model.Figure;

import javax.validation.constraints.NotNull;

public class ReadyDTO {

    @NotNull
    private boolean isReady;
    private Figure[][] area;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public Figure[][] getArea() {
        return area;
    }

    public void setArea(Figure[][] area) {
        this.area = area;
    }
}
