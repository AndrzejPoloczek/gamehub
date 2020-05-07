package gamehub.gamebind.service;

import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.exception.GameCancelException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.Player;
import gamehub.sdk.enums.GameType;

import java.util.List;

public interface GameBindService {

    /**
     * Create a new game bind
     * @param bind Game bind object to create
     * @return
     * @throws GameBindException
     */
    GameBind create(GameBind bind) throws GameBindException;

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

    /**
     * CHeck current game state.
     * @param guid
     * @return
     */
    GameBind updatePlayerStatus(String guid, String username) throws GameBindException;

    /**
     * Cancel game by user
     * @param guid
     * @param username
     * @return
     * @throws GameBindException
     */
    void cancel(String guid, String username) throws GameBindException, GameCancelException;
}
