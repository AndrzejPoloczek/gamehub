package gamehub.gamebind.component;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import gamehub.gamebind.clients.GameClient;
import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.exception.GamePlayGuidException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.converter.PlayerReverseConverter;
import gamehub.gamebind.service.GamePlayService;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
@Scope("prototype")
public class GamePlayGuidRetryable {

    private static final Logger LOG = LogManager.getLogger(GamePlayGuidRetryable.class);

    private GamePlayService gamePlayService;
    private PlayerReverseConverter playerReverseConverter;

    @Retryable(
            value = {GamePlayGuidException.class, HystrixBadRequestException.class},
            maxAttempts = 10,
            backoff = @Backoff(10000)
    )
    public String getGuid(final GameBind gameBind) throws GamePlayGuidException, GameBindException {
        GameClient gameClient = gamePlayService.getClient(gameBind.getType())
                .orElseThrow(() -> new GameBindException("No game client found fot type " + gameBind.getType()));
        ResponseEntity<String> guid = gameClient.create(prepareCreateDTO(gameBind));
        if (guid.getStatusCode().equals(HttpStatus.OK)) {
            return guid.getBody();
        }
        LOG.error(String.format("Unable to get game play gui, got status: %s", guid.getStatusCode()));
        throw new GamePlayGuidException("Unable to retrieve game play guid");
    }

    private GameCreateDTO prepareCreateDTO(final GameBind gameBind) {
        final GameCreateDTO createDTO = new GameCreateDTO();
        createDTO.setPlayers(gameBind.getPlayers().stream()
                .map(playerReverseConverter::convert)
                .collect(Collectors.toList()));
        return createDTO;
    }

    @Autowired
    public void setGamePlayService(GamePlayService gamePlayService) {
        this.gamePlayService = gamePlayService;
    }

    @Autowired
    public void setPlayerReverseConverter(PlayerReverseConverter playerReverseConverter) {
        this.playerReverseConverter = playerReverseConverter;
    }
}
