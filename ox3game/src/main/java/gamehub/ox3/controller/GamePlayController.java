package gamehub.ox3.controller;

import gamehub.ox3.exception.GamePlayException;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class GamePlayController {

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody final GameCreateDTO game) throws GamePlayException {
        System.out.println("* * * * * * OK");
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}
