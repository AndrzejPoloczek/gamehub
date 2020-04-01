package gamehub.api.dto;

import javax.validation.constraints.NotNull;

public class ApiReadyDTO {

    @NotNull
    private boolean isReady;
    private String[][] area;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String[][] getArea() {
        return area;
    }

    public void setArea(String[][] area) {
        this.area = area;
    }
}
