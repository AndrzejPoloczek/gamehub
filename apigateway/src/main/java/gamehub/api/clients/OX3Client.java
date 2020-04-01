package gamehub.api.clients;

import gamehub.api.config.FeignClientRequestConfiguration;
import gamehub.api.dto.ApiReadyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ox3-game-service", configuration = FeignClientRequestConfiguration.class)
public interface OX3Client {

    @PutMapping(path = "/ready/confirm/{guid}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiReadyDTO> readyConfirm(@PathVariable final String guid, @PathVariable final String username);

    @GetMapping(path = "/ready/check/{guid}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiReadyDTO> readyCheck(@PathVariable final String guid, @PathVariable final String username);
}
