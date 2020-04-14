package gamehub.ox3.facade.impl;

import gamehub.ox3.converter.StateToMoveConverter;
import gamehub.ox3.dto.PlayDTO;
import gamehub.ox3.exception.GamePlayException;
import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.facade.GamePlayFacade;
import gamehub.ox3.model.GameState;
import gamehub.ox3.service.GamePlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GamePlayFacadeImpl implements GamePlayFacade {

    private GamePlayService gamePlayService;
    private StateToMoveConverter stateToMoveConverter;

    @Override
    public PlayDTO performMove(String guid, String username, int x, int y) throws GamePlayException, GameStateException {
        final GameState state = gamePlayService.performMove(guid, username, x, y);
        final PlayDTO playDTO = stateToMoveConverter.convert(state);
        playDTO.setCanMove(gamePlayService.canMove(state, username));
        playDTO.setWinner(gamePlayService.isWinner(state, username));
        return playDTO;
    }

    @Override
    public PlayDTO performCheck(String guid, String username) throws GameStateException {
        final GameState state = gamePlayService.checkState(guid, username);
        final PlayDTO playDTO = stateToMoveConverter.convert(state);
        playDTO.setCanMove(gamePlayService.canMove(state, username));
        playDTO.setWinner(gamePlayService.isWinner(state, username));
        return playDTO;
    }

    @Autowired
    public void setGamePlayService(GamePlayService gamePlayService) {
        this.gamePlayService = gamePlayService;
    }

    @Autowired
    public void setStateToMoveConverter(StateToMoveConverter stateToMoveConverter) {
        this.stateToMoveConverter = stateToMoveConverter;
    }
}
