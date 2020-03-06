package gamehub.gamebind.component;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import gamehub.gamebind.clients.GamePlayClient;
import gamehub.gamebind.exception.GamePlayGuidException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.converter.PlayerReverseConverter;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
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

    private GamePlayClient gamePlayClient;
    private PlayerReverseConverter playerReverseConverter;

    @Retryable(
            value = {GamePlayGuidException.class, HystrixBadRequestException.class},
            maxAttempts = 10,
            backoff = @Backoff(10000)
    )
    public String getGuid(final GameBind gameBind) throws GamePlayGuidException {
        ResponseEntity<String> guid = gamePlayClient.create(prepareCreateTTO(gameBind));
        if (guid.getStatusCode().equals(HttpStatus.OK)) {
            return guid.getBody();
        }
        throw new GamePlayGuidException("Unable to retrieve game play guid");
    }

    private GameCreateDTO prepareCreateTTO(final GameBind gameBind) {
        final GameCreateDTO createDTO = new GameCreateDTO();
        createDTO.setGameType(gameBind.getType());
        createDTO.setPlayers(gameBind.getPlayers().stream()
                .map(playerReverseConverter::convert)
                .collect(Collectors.toList()));
        return createDTO;
    }

    @Autowired
    public void setGamePlayClient(GamePlayClient gamePlayClient) {
        this.gamePlayClient = gamePlayClient;
    }

    @Autowired
    public void setPlayerReverseConverter(PlayerReverseConverter playerReverseConverter) {
        this.playerReverseConverter = playerReverseConverter;
    }
}
