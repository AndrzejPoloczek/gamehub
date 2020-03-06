package gamehub.sdk.dto.gameplay;

import gamehub.sdk.dto.gamebind.PlayerDTO;
import gamehub.sdk.enums.GameType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class GameCreateDTO {

    @NotNull
    private GameType gameType;

    @Valid
    @NotEmpty
    private List<PlayerDTO> players;

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
