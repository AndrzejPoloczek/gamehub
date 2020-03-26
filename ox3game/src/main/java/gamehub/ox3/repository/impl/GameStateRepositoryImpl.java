package gamehub.ox3.repository.impl;

import gamehub.ox3.model.GameState;
import gamehub.ox3.repository.GameStateRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class GameStateRepositoryImpl implements GameStateRepository {

    private Map<String, GameState> states = new HashMap<>();

    @Override
    public GameState create(GameState state) {
        state.setGuid(UUID.randomUUID().toString());
        states.put(state.getGuid(), state);
        return state;
    }
}
