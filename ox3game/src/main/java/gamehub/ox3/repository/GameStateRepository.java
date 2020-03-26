package gamehub.ox3.repository;

import gamehub.ox3.model.GameState;

public interface GameStateRepository {

    GameState create(GameState state);
}
