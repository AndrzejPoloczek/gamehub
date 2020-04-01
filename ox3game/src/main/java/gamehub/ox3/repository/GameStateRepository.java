package gamehub.ox3.repository;

import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.GameState;

public interface GameStateRepository {

    GameState create(GameState state);
    GameState findById(String guid) throws GameStateException;
    GameState update(GameState state) throws GameStateException;
}
