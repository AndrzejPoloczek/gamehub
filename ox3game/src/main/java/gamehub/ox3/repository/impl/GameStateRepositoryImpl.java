package gamehub.ox3.repository.impl;

import gamehub.ox3.exception.GameStateException;
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

    @Override
    public GameState findById(String guid) throws GameStateException {
        if (!states.containsKey(guid)) {
            throw new GameStateException(String.format("Game %s not found", guid));
        }
        return states.get(guid);
    }

    @Override
    public GameState update(GameState state) throws GameStateException {
        if (!states.containsKey(state.getGuid())) {
            throw new GameStateException(String.format("Game %s not found", state.getGuid()));
        }
        states.remove(state.getGuid());
        states.put(state.getGuid(), state);
        return state;
    }
}
