package gamehub.api.controller;

import gamehub.api.clients.GameBindClient;
import gamehub.api.dto.ApiCurrentGameDTO;
import gamehub.api.dto.ApiGameCreateDTO;
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
public class GameBindController extends AbstractController {

    private GameBindClient gameBindClient;

    @ApiOperation(value = "Create a new game", response = GameBindDTO.class)
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBindDTO> create(@RequestBody ApiGameCreateDTO form) {
        validateSessionUser();
        validateCurrentBind();

        GameCreateDTO create = new GameCreateDTO();
        create.setType(form.getType());
        create.setPlayers(form.getPlayers());
        populateSessionPlayer(create);

        final ResponseEntity<GameBindDTO> bind = gameBindClient.create(create);
        getSessionUser().setCurrentBind(bind.getBody().getGuid());
        return bind;
    }

    @ApiOperation(value = "Create existing game", response = GameBindDTO.class)
    @PutMapping(path = "/join/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBindDTO> join(@PathVariable String guid) {
        validateSessionUser();
        validateCurrentBind();

        GameJoinDTO join = new GameJoinDTO();
        join.setGuid(guid);
        populateSessionPlayer(join);

        final ResponseEntity<GameBindDTO> bind = gameBindClient.join(join);
        getSessionUser().setCurrentBind(bind.getBody().getGuid());
        return bind;
    }

    @ApiOperation(value = "Create available games", response = List.class)
    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameBindDTO>> find() {
        validateSessionUser();
        return gameBindClient.find();
    }

    @ApiOperation(value = "Create available games by type", response = List.class)
    @GetMapping(path = "/find/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameBindDTO>> findByType(@PathVariable String type) {
        validateSessionUser();
        return gameBindClient.findByType(type);
    }

    @ApiOperation(value = "Check game status and update player status if necessary", response = GameBindCheckDTO.class)
    @PatchMapping(path = "/update/player/status/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBindCheckDTO> updatePlayerStatus(@PathVariable final String guid) {
        validateSessionUser();
        return gameBindClient.updatePlayerStatus(guid, getSessionUser().getUsername());
    }

    @GetMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameDefinitionDTO>> getAvailableGames() {
        return gameBindClient.getAvailableGames();
    }

    @GetMapping(path = "/games/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameDefinitionDTO> getGame(@PathVariable String type) {
        return gameBindClient.getGame(type);
    }

    @GetMapping(path = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiCurrentGameDTO> getCurrentBind() {
        validateSessionUser();
        return ResponseEntity.ok(new ApiCurrentGameDTO(getSessionUser().getCurrentBind()));
    }

    @Autowired
    public void setGameBindClient(GameBindClient gameBindClient) {
        this.gameBindClient = gameBindClient;
    }

    private void populateSessionPlayer(PlayerDTO playerDTO) {
        playerDTO.setUsername(getSessionUser().getUsername());
        playerDTO.setDisplayName(getSessionUser().getDisplayName());
    }
}
