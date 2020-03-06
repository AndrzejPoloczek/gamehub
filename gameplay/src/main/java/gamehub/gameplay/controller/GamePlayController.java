package gamehub.gameplay.controller;

import gamehub.gameplay.converters.GameCreateToGamePlayConverter;
import gamehub.gameplay.exception.GamePlayException;
import gamehub.gameplay.model.GamePlay;
import gamehub.gameplay.service.GamePlayService;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
public class GamePlayController {

    private GamePlayService gamePlayService;
    private GameCreateToGamePlayConverter gameCreateToGamePlayConverter;


    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody final GameCreateDTO game) throws GamePlayException {
        return ResponseEntity.ok(gamePlayService.create(gameCreateToGamePlayConverter.convert(game)).getGuid());
    }

    @GetMapping(path = "/get/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GamePlay> find(@PathVariable @NotBlank String guid) throws GamePlayException {
        return gamePlayService.get(guid)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new GamePlayException(String.format("Game with guid %s not found", guid)));
    }


    @Autowired
    public void setGamePlayService(GamePlayService gamePlayService) {
        this.gamePlayService = gamePlayService;
    }

    @Autowired
    public void setGameCreateToGamePlayConverter(GameCreateToGamePlayConverter gameCreateToGamePlayConverter) {
        this.gameCreateToGamePlayConverter = gameCreateToGamePlayConverter;
    }
}
