package gamehub.gamebind.service;

import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.Player;
import gamehub.sdk.enums.GameType;

import java.util.List;

public interface GameBindService {

    /**
     * Create a new game bind
     * @param gameBind Game bind object to create
     * @return
     * @throws GameBindException
     */
    GameBind create(GameBind gameBind) throws GameBindException;

    /**
     * Get all available games
     * @return List of available games
     */
    List<GameBind> getAvailableGames();

    /**
     * Get all available games with selected type.
     * @param type Game type
     * @return
     */
    List<GameBind> getAvailableGames(GameType type);

    /**
     * Join an existing and available game
     * @param guid
     * @param player
     * @return
     */
    GameBind join(String guid, Player player) throws GameBindException;
}
