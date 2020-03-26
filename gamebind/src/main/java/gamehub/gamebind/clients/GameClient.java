package gamehub.gamebind.clients;

import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface GameClient {

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> create(final GameCreateDTO game);
}
