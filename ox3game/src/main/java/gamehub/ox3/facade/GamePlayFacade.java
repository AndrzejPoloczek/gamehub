package gamehub.ox3.facade;

import gamehub.ox3.dto.PlayDTO;
import gamehub.ox3.exception.GamePlayException;
import gamehub.ox3.exception.GameStateException;

public interface GamePlayFacade {

    PlayDTO performMove(String guid, String  username, int x, int y) throws GamePlayException, GameStateException;

    PlayDTO performCheck(String guid, String username) throws GameStateException;
}
