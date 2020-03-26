package gamehub.ox3.controller;

import gamehub.ox3.converter.InitialPlayersConverter;
import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.GameState;
import gamehub.ox3.service.GameStateService;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GamePlayController {

    private GameStateService gameStateService;
    private InitialPlayersConverter initialPlayersConverter;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody final GameCreateDTO game) throws GameStateException {
        final GameState gameState = gameStateService.create(initialPlayersConverter.convert(game));
        dumpGameState(gameState);
        return ResponseEntity.ok(gameState.getGuid());
    }

    // TODO just for test task GH-26 - to remove in future
    private void dumpGameState(GameState state) {
        final StringBuilder sb = new StringBuilder();
        final String pattern = "%s=%s";
        sb.append("* * * dump GameState(")
                .append(String.format(pattern, "guid", state.getGuid())).append(",")
                .append(String.format(pattern, "players", state.getPlayers())).append(",")
                .append(String.format(pattern, "figures", state.getFigures())).append(",")
                .append(String.format(pattern, "current", state.getCurrent())).append(",")
                .append(String.format(pattern, "state", state.getState())).append(")");
        System.out.println(sb.toString());
    }

    @Autowired
    public void setGameStateService(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    @Autowired
    public void setInitialPlayersConverter(InitialPlayersConverter initialPlayersConverter) {
        this.initialPlayersConverter = initialPlayersConverter;
    }
}
