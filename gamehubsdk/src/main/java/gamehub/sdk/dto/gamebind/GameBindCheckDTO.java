package gamehub.sdk.dto.gamebind;

public class GameBindCheckDTO {

    private boolean ready;
    private boolean canceled;
    private String playGuid;

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getPlayGuid() {
        return playGuid;
    }

    public void setPlayGuid(String playGuid) {
        this.playGuid = playGuid;
    }
}
