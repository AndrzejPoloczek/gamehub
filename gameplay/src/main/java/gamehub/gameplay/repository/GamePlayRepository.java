package gamehub.gameplay.repository;

import gamehub.gameplay.model.GamePlay;

import java.util.Optional;

public interface GamePlayRepository {

    /**
     *Insert a new game play object
     * @param gamePlay
     * @return
     */
    GamePlay insert(GamePlay gamePlay);

    /**
     * Get existing game play object
     * @param guid
     * @return
     */
    Optional<GamePlay> get(String guid);
}
