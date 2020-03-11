package gamehub.sdk.dto.gamebind;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Game bind check results")
public class GameBindCheckDTO {

    @ApiModelProperty(required = true, name = "Game bind ready flag", notes = "Inform, if game is ready to play")
    private boolean ready;

    @ApiModelProperty(required = true, name = "Game bind canceled flag", notes = "Inform, if game bind has been canceled")
    private boolean canceled;

    @ApiModelProperty(required = true, name = "Game play guid", notes = "Generated unique game play guid after successful binding")
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
