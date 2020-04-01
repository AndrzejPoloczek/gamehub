package gamehub.ox3.service;

import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.GameState;

import java.util.Map;

public interface GameStateService {

    GameState create(Map<String, String> players) throws GameStateException;
    GameState get(String guid, String username) throws GameStateException;
    GameState confirmReady(String guid, String username) throws GameStateException;
    boolean isReady(String guid, String username) throws GameStateException;

    /**
     * Check, if game is ready do play
     * @param state
     * @return
     */
    boolean isReady(GameState state);
}
