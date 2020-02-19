package gamehub.gamebind.service;

import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameType;
import gamehub.gamebind.model.Player;

import java.util.List;

public interface GameBindService {

    /**
     * Create a new game bind
     * @param type Game type
     * @param creator Game creator
     * @param players Game players count
     * @return
     * @throws GameBindException
     */
    GameBind create(GameType type, Player creator, int players) throws GameBindException;

    /**
     * Get all available games
     * @return List of available games
     */
    List<GameBind> getAvailableGames();
}
