package gamehub.api.clients;

import gamehub.api.config.FeignClientRequestConfiguration;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import gamehub.sdk.dto.gamebind.GameCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "game-bind-service", configuration = FeignClientRequestConfiguration.class)
public interface GameBindClient {

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GameBindDTO> create(@RequestBody GameCreateDTO form);

    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GameBindDTO>> find();
}
