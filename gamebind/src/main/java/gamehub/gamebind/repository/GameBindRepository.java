package gamehub.gamebind.repository;

import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameType;
import gamehub.gamebind.model.Player;

import java.util.List;

public interface GameBindRepository {

    GameBind insert(GameBind gameBind);
    GameBind join(String guid, Player player) throws GameBindException;
    GameBind findByGuid(String guid) throws GameBindException;
    List<GameBind> findAvailable();
    List<GameBind> findAvailable(GameType type);
}
