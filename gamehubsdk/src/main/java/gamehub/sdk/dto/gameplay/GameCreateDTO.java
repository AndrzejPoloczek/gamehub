package gamehub.sdk.dto.gameplay;

import gamehub.sdk.dto.gamebind.PlayerDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class GameCreateDTO {

    @Valid
    @NotEmpty
    private List<PlayerDTO> players;

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
