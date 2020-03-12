package gamehub.sdk.dto.gamebind;

import gamehub.sdk.enums.GameType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Game definition")
public class GameDefinitionDTO {

    @ApiModelProperty(required = true, name = "Type", notes = "Type of current game")
    private GameType type;

    @ApiModelProperty(required = true, name = "Name", notes = "Name of current game")
    private String name;

    @ApiModelProperty(required = true, name = "Description", notes = "Description of current game")
    private String description;

    @ApiModelProperty(required = true, name = "Minimal players", notes = "Minimal players amount need to play game")
    private int minPlayers;

    @ApiModelProperty(required = true, name = "Maximal players", notes = "Maximal players amount need to play game")
    private int maxPlayers;

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
