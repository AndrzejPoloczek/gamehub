package gamehub.gameplay.service;

import gamehub.gameplay.exception.GamePlayException;
import gamehub.gameplay.model.GamePlay;

import java.util.Optional;

public interface GamePlayService {

    /**
     * Create a new game paly object
     * @param gamePlay Game play object
     * @return
     */
    GamePlay create(GamePlay gamePlay) throws GamePlayException;

    /**
     * Get existing gampe play bject
     * @param guid
     * @return
     */
    Optional<GamePlay> get(String guid) throws GamePlayException;
}
