package gamehub.api.facade.impl;

import gamehub.api.clients.GameBindClient;
import gamehub.api.dto.ApiCurrentGameDTO;
import gamehub.api.dto.ApiGameCreateDTO;
import gamehub.api.facade.GameBindFacade;
import gamehub.api.facade.SessionUserFacade;
import gamehub.sdk.dto.gamebind.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameBindFacadeImpl implements GameBindFacade {

    private GameBindClient gameBindClient;
    private SessionUserFacade sessionUserFacade;

    @Override
    public ResponseEntity<GameBindDTO> create(ApiGameCreateDTO form) {
        sessionUserFacade.validateSessionUser();
        validateCurrentBindEmpty();
        GameCreateDTO create = new GameCreateDTO();
        create.setType(form.getType());
        create.setPlayers(form.getPlayers());
        populateSessionPlayer(create);

        final ResponseEntity<GameBindDTO> response = gameBindClient.create(create);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            sessionUserFacade.getSessionUser().setCurrentBind(response.getBody().getGuid());
        }
        return response;
    }

    @Override
    public ResponseEntity<GameBindDTO> join(String guid) {
        sessionUserFacade.validateSessionUser();
        validateCurrentBindEmpty();
        GameJoinDTO join = new GameJoinDTO();
        join.setGuid(guid);
        populateSessionPlayer(join);

        final ResponseEntity<GameBindDTO> response = gameBindClient.join(join);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            sessionUserFacade.getSessionUser().setCurrentBind(response.getBody().getGuid());
        }
        return response;
    }

    @Override
    public ResponseEntity<List<GameBindDTO>> find() {
        sessionUserFacade.validateSessionUser();
        return gameBindClient.find();
    }

    @Override
    public ResponseEntity<List<GameBindDTO>> findByType(String type) {
        sessionUserFacade.validateSessionUser();
        return gameBindClient.findByType(type);
    }

    @Override
    public ResponseEntity<GameBindCheckDTO> updatePlayerStatus(String guid) {
        sessionUserFacade.validateSessionUser();
        ResponseEntity<GameBindCheckDTO> response = gameBindClient.updatePlayerStatus(guid, sessionUserFacade.getSessionUser().getUsername());
        if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().isCanceled()) {
            sessionUserFacade.getSessionUser().setCurrentBind(null);
        }
        return response;
    }

    @Override
    public ResponseEntity<ApiCurrentGameDTO> getCurrentBind() {
        sessionUserFacade.validateSessionUser();
        return ResponseEntity.ok(new ApiCurrentGameDTO(sessionUserFacade.getSessionUser().getCurrentBind()));
    }

    @Override
    public ResponseEntity<Object> cancelBind(String guid) {
        sessionUserFacade.validateSessionUser();
        validateBindCancel();
        ResponseEntity<Object> response = gameBindClient.cancelBind(guid, sessionUserFacade.getSessionUser().getUsername());
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            sessionUserFacade.getSessionUser().setCurrentBind(null);
        }
        return response;
    }


    private void populateSessionPlayer(PlayerDTO playerDTO) {
        playerDTO.setUsername(sessionUserFacade.getSessionUser().getUsername());
        playerDTO.setDisplayName(sessionUserFacade.getSessionUser().getDisplayName());
    }

    private void validateCurrentBindEmpty() {
        if (StringUtils.isNotBlank(sessionUserFacade.getSessionUser().getCurrentBind())) {
            throw new IllegalStateException("You already bind other game.");
        }
    }

    private void validateBindCancel() {
        if (StringUtils.isBlank(sessionUserFacade.getSessionUser().getCurrentBind())) {
            throw new IllegalStateException("You have no game to cancel");
        }
    }


    @Autowired
    public void setGameBindClient(GameBindClient gameBindClient) {
        this.gameBindClient = gameBindClient;
    }

    @Autowired
    public void setSessionUserFacade(SessionUserFacade sessionUserFacade) {
        this.sessionUserFacade = sessionUserFacade;
    }
}
