package gamehub.gamebind.clients;

import gamehub.gamebind.config.FeignClientRequestConfiguration;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "game-play-service", configuration = FeignClientRequestConfiguration.class)
public interface GamePlayClient {

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> create(final GameCreateDTO game);
}
