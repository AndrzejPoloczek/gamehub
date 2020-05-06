package gamehub.gamebind.strategy;

import gamehub.gamebind.exception.GameCancelException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import org.springframework.stereotype.Component;

@Component
public class CancelOwnerStrategy implements CancelStrategy {

    @Override
    public void cancel(GameBind bind, String username) throws GameCancelException {
        bind.setStatus(GameBindStatus.CANCELED);
    }
}
