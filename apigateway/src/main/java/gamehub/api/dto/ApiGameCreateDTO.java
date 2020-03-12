package gamehub.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "Game create data")
public class ApiGameCreateDTO {

    @ApiModelProperty(required = true, name = "Game type", notes = "Chosen game type")
    @NotNull
    private String type;

    @ApiModelProperty(required = true, name = "Expected players amount", notes = "Expected players amount need to start game")
    @Min(1L)
    private int players;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }
}
