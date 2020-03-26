package gamehub.ox3.service;

import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.GameState;

import java.util.Map;

public interface GameStateService {

    GameState create(Map<String, String> players) throws GameStateException;
}
