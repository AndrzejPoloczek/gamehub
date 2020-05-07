package gamehub.gamebind.strategy;

import gamehub.gamebind.exception.GameCancelException;
import gamehub.gamebind.model.GameBind;
import org.springframework.stereotype.Component;

@Component
public class CancelJoinerStrategy implements CancelStrategy {

    @Override
    public void cancel(GameBind bind, String username) throws GameCancelException {
        validate(bind, username);
        bind.getPlayers().removeIf(player -> player.getUsername().equals(username));
    }

    private void validate(GameBind bind, String username) throws GameCancelException {
        bind.getPlayers()
                .stream()
                .filter(player -> player.getUsername().contains(username))
                .findFirst()
                .orElseThrow(() -> new GameCancelException("You are not allowed to cancel this game"));
    }
}
