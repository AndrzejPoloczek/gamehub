package gamehub.api.controller;

import gamehub.api.clients.OX3Client;
import gamehub.api.dto.ApiPlayDTO;
import gamehub.api.dto.ApiReadyDTO;
import gamehub.api.facade.SessionUserFacade;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ox3")
@Api(tags = "OX3 Game Controller")
public class OX3Controller {

    private OX3Client ox3Client;
    private SessionUserFacade sessionUserFacade;

    @PutMapping(path = "/ready/confirm/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReadyDTO> readyConfirm(@PathVariable final String guid) {
        sessionUserFacade.validateSessionUser();
        return ox3Client.readyConfirm(guid, sessionUserFacade.getSessionUser().getUsername());
    }

    @GetMapping(path = "/ready/check/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReadyDTO> readyCheck(@PathVariable final String guid) {
        sessionUserFacade.validateSessionUser();
        return ox3Client.readyCheck(guid, sessionUserFacade.getSessionUser().getUsername());
    }

    @PutMapping(path = "/play/move/{guid}/{x}/{y}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiPlayDTO> playMove(@PathVariable final String guid, @PathVariable final int x, @PathVariable final int y){
        sessionUserFacade.validateSessionUser();
        return ox3Client.playMove(guid, sessionUserFacade.getSessionUser().getUsername(), x, y);
    }

    @GetMapping(path = "/play/check/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiPlayDTO> playCheck(@PathVariable final String guid) {
        sessionUserFacade.validateSessionUser();
        return ox3Client.playCheck(guid, sessionUserFacade.getSessionUser().getUsername());
    }

    @Autowired
    public void setOx3Client(OX3Client ox3Client) {
        this.ox3Client = ox3Client;
    }


    @Autowired
    public void setSessionUserFacade(SessionUserFacade sessionUserFacade) {
        this.sessionUserFacade = sessionUserFacade;
    }
}
