package gamehub.gamebind.repository;

import gamehub.gamebind.model.GameBind;

import java.util.List;

public interface GameBindRepository {

    GameBind insert(GameBind gameBind);
    List<GameBind> findAvailable();
}
