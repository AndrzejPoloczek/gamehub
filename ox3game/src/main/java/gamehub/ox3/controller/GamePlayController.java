package gamehub.ox3.controller;

import gamehub.ox3.converter.InitialPlayersConverter;
import gamehub.ox3.converter.StateToReadyConverter;
import gamehub.ox3.dto.PlayDTO;
import gamehub.ox3.dto.ReadyDTO;
import gamehub.ox3.exception.GamePlayException;
import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.facade.GamePlayFacade;
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
    private StateToReadyConverter stateToReadyConverter;
    private GamePlayFacade gamePlayFacade;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody final GameCreateDTO game) throws GameStateException {
        final GameState gameState = gameStateService.create(initialPlayersConverter.convert(game));
        return ResponseEntity.ok(gameState.getGuid());
    }

    @PutMapping(path = "/ready/confirm/{guid}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadyDTO> readyConfirm(@PathVariable final String guid, @PathVariable final String username) throws GameStateException {
        return ResponseEntity.ok(stateToReadyConverter.convert(gameStateService.confirmReady(guid, username)));
    }

    @GetMapping(path = "/ready/check/{guid}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadyDTO> readyCheck(@PathVariable final String guid, @PathVariable final String username) throws GameStateException {
        return ResponseEntity.ok(stateToReadyConverter.convert(gameStateService.get(guid, username)));
    }

    @PutMapping(path = "/play/move/{guid}/{username}/{x}/{y}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayDTO> playMove(@PathVariable final String guid, @PathVariable final String username,
                                            @PathVariable final int x, @PathVariable final int y) throws GameStateException, GamePlayException {
        return ResponseEntity.ok(gamePlayFacade.performMove(guid, username, x, y));
    }

    @GetMapping(path = "/play/check/{guid}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayDTO> playCheck(@PathVariable final String guid, @PathVariable final String username) throws GameStateException {

        return ResponseEntity.ok(gamePlayFacade.performCheck(guid, username));
    }

    @Autowired
    public void setGameStateService(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    @Autowired
    public void setInitialPlayersConverter(InitialPlayersConverter initialPlayersConverter) {
        this.initialPlayersConverter = initialPlayersConverter;
    }

    @Autowired
    public void setStateToReadyConverter(StateToReadyConverter stateToReadyConverter) {
        this.stateToReadyConverter = stateToReadyConverter;
    }

    @Autowired
    public void setGamePlayFacade(GamePlayFacade gamePlayFacade) {
        this.gamePlayFacade = gamePlayFacade;
    }
}
