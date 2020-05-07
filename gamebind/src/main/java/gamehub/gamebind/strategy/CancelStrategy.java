package gamehub.gamebind.strategy;

import gamehub.gamebind.exception.GameCancelException;
import gamehub.gamebind.model.GameBind;

public interface CancelStrategy {

    void cancel(GameBind bind, String username) throws GameCancelException;
}
