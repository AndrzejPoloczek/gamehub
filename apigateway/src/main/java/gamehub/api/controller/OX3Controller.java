package gamehub.api.controller;

import gamehub.api.clients.OX3Client;
import gamehub.api.dto.ApiPlayDTO;
import gamehub.api.dto.ApiReadyDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ox3")
@Api(tags = "OX3 Game Controller")
public class OX3Controller extends AbstractController {

    private OX3Client ox3Client;

    @PutMapping(path = "/ready/confirm/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReadyDTO> readyConfirm(@PathVariable final String guid) {
        validateSessionUser();
        return ox3Client.readyConfirm(guid, getSessionUser().getUsername());
    }

    @GetMapping(path = "/ready/check/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReadyDTO> readyCheck(@PathVariable final String guid) {
        validateSessionUser();
        return ox3Client.readyCheck(guid, getSessionUser().getUsername());
    }

    @PutMapping(path = "/play/move/{guid}/{x}/{y}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiPlayDTO> playMove(@PathVariable final String guid, @PathVariable final int x, @PathVariable final int y){
        validateSessionUser();
        return ox3Client.playMove(guid, getSessionUser().getUsername(), x, y);
    }

    @GetMapping(path = "/play/check/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiPlayDTO> playCheck(@PathVariable final String guid) {
        validateSessionUser();
        return ox3Client.playCheck(guid, getSessionUser().getUsername());
    }

    @Autowired
    public void setOx3Client(OX3Client ox3Client) {
        this.ox3Client = ox3Client;
    }
}
