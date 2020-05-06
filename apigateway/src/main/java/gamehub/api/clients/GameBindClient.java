package gamehub.api.clients;

import gamehub.api.config.FeignClientRequestConfiguration;
import gamehub.sdk.dto.gamebind.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "game-bind-service", configuration = FeignClientRequestConfiguration.class)
public interface GameBindClient {

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GameBindDTO> create(@RequestBody GameCreateDTO form);

    @PutMapping(path = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GameBindDTO> join(@RequestBody GameJoinDTO form);

    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GameBindDTO>> find();

    @GetMapping(path = "/find/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GameBindDTO>> findByType(@PathVariable String type);

    @PutMapping(path = "/update/player/status/{guid}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GameBindCheckDTO> updatePlayerStatus(@PathVariable final String guid, @PathVariable final String username);

    @GetMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GameDefinitionDTO>> getAvailableGames();

    @GetMapping(path = "/games/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GameDefinitionDTO> getGame(@PathVariable String type);

    @GetMapping(path = "/cancel/{guid}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> cancelBind(@PathVariable final String guid, @PathVariable final String username);
}
