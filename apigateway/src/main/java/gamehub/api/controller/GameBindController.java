package gamehub.api.controller;

import gamehub.api.clients.GameBindClient;
import gamehub.api.dto.ApiGameCreateDTO;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import gamehub.sdk.dto.gamebind.GameCreateDTO;
import gamehub.sdk.dto.gamebind.GameJoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/game/bind")
public class GameBindController extends AbstractController {

    private GameBindClient gameBindClient;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBindDTO> create(@RequestBody ApiGameCreateDTO form) {
        GameCreateDTO create = new GameCreateDTO();
        create.setUsername(getValidSessionUsername());
        create.setDisplayName(getSessionUser().getDisplayName());
        create.setType(form.getType());
        create.setPlayers(form.getPlayers());
        return gameBindClient.create(create);
    }

    @PutMapping(path = "/join/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameBindDTO> join(@PathVariable String guid) {
        GameJoinDTO join = new GameJoinDTO();
        join.setGuid(guid);
        join.setUsername(getValidSessionUsername());
        join.setDisplayName(getSessionUser().getDisplayName());
        return gameBindClient.join(join);
    }

    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameBindDTO>> find() {
        validateSessionUser();
        return gameBindClient.find();
    }

    @GetMapping(path = "/find/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameBindDTO>> findByType(@PathVariable String type) {
        validateSessionUser();
        return gameBindClient.findByType(type);
    }

    @Autowired
    public void setGameBindClient(GameBindClient gameBindClient) {
        this.gameBindClient = gameBindClient;
    }
}
