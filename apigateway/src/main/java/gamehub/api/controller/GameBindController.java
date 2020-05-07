package gamehub.api.controller;

import gamehub.api.clients.GameBindClient;
import gamehub.api.dto.ApiCurrentGameDTO;
import gamehub.api.dto.ApiGameCreateDTO;
import gamehub.api.facade.GameBindFacade;
import gamehub.sdk.dto.gamebind.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/game/bind")
@Api(tags = "Game bind process")
public class GameBindController {

    private GameBindFacade gameBindFacade;
    private GameBindClient gameBindClient;

    @ApiOperation(value = "Create a new game", response = GameBindDTO.class)
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBindDTO> create(@RequestBody ApiGameCreateDTO form) {
        return gameBindFacade.create(form);
    }

    @ApiOperation(value = "Create existing game", response = GameBindDTO.class)
    @PutMapping(path = "/join/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBindDTO> join(@PathVariable String guid) {
        return gameBindFacade.join(guid);
    }

    @ApiOperation(value = "Create available games", response = List.class)
    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameBindDTO>> find() {
        return gameBindFacade.find();
    }

    @ApiOperation(value = "Create available games by type", response = List.class)
    @GetMapping(path = "/find/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameBindDTO>> findByType(@PathVariable String type) {
        return gameBindFacade.findByType(type);
    }

    @ApiOperation(value = "Check game status and update player status if necessary", response = GameBindCheckDTO.class)
    @PatchMapping(path = "/update/player/status/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBindCheckDTO> updatePlayerStatus(@PathVariable final String guid) {
        return gameBindFacade.updatePlayerStatus(guid);
    }

    @ApiOperation(value = "Get available game definitions", response = GameDefinitionDTO.class)
    @GetMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameDefinitionDTO>> getAvailableGames() {
        return gameBindClient.getAvailableGames();
    }

    @ApiOperation(value = "Get available game definition by type", response = GameDefinitionDTO.class)
    @GetMapping(path = "/games/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameDefinitionDTO> getGame(@PathVariable String type) {
        return gameBindClient.getGame(type);
    }

    @ApiOperation(value = "Get current game bind guid, if exists", response = ApiCurrentGameDTO.class)
    @GetMapping(path = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiCurrentGameDTO> getCurrentBind() {
        return gameBindFacade.getCurrentBind();
    }

    @ApiOperation(value = "Cancel current bind game", response = Boolean.class)
    @PutMapping(path = "/cancel/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> cancelBind(@PathVariable final String guid) {
        return gameBindFacade.cancelBind(guid);
    }


    @Autowired
    public void setGameBindFacade(GameBindFacade gameBindFacade) {
        this.gameBindFacade = gameBindFacade;
    }

    @Autowired
    public void setGameBindClient(GameBindClient gameBindClient) {
        this.gameBindClient = gameBindClient;
    }
}
