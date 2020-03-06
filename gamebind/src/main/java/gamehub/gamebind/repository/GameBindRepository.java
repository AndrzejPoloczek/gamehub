package gamehub.gamebind.repository;

import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.gamebind.model.Player;
import gamehub.sdk.enums.GameType;

import java.util.List;
import java.util.Optional;

public interface GameBindRepository {

    GameBind insert(GameBind gameBind);
    GameBind join(String guid, Player player) throws GameBindException;
    GameBind findByGuid(String guid) throws GameBindException;
    List<GameBind> findAvailable();
    List<GameBind> findAvailable(GameType type);
    void update(String guid, Optional<GameBindStatus> newStatus, Optional<String> gamePlayGuid) throws GameBindException;
}
