package gamehub.ox3.service;

import gamehub.ox3.exception.GamePlayException;
import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.GameState;

public interface GamePlayService {

    /**
     * Perform move on specified coordinates
     * @param guid Game guid
     * @param username Username
     * @param x X coordinate
     * @param y Y coordinate
     * @return
     * @throws GameStateException
     * @throws GamePlayException
     */
    GameState performMove(String guid, String username, int x, int y) throws GameStateException, GamePlayException;

    /**
     * Check for players tour
     * @param guid
     * @param username
     * @return
     */
    GameState checkState(String guid, String username) throws GameStateException;

    /**
     * Can player move
     * @param state
     * @param username
     * @return
     */
    boolean canMove(GameState state, String username) throws GameStateException;

    /**
     * Is current user winner
     * @param state
     * @param username
     * @return
     * @throws GameStateException
     */
    boolean isWinner(GameState state, String username) throws GameStateException;
}
