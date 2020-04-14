package gamehub.ox3.service.impl;

import gamehub.ox3.exception.GamePlayException;
import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.Figure;
import gamehub.ox3.model.GameState;
import gamehub.ox3.model.OX3State;
import gamehub.ox3.repository.GameStateRepository;
import gamehub.ox3.service.GamePlayService;
import gamehub.ox3.validation.GamePlayValidation;
import gamehub.ox3.validation.GameStateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GamePlayServiceImpl implements GamePlayService {

    private GameStateRepository gameStateRepository;
    private GameStateValidation gameStateValidation;
    private GamePlayValidation gamePlayValidation;

    @Override
    public GameState performMove(String guid, String username, int x, int y) throws GameStateException, GamePlayException {
        final GameState state = gameStateRepository.findById(guid);
        gameStateValidation.validatePlayActionsAllowed(state, username);
        gamePlayValidation.validateTour(state, username);
        gamePlayValidation.validateMove(state, x, y);
        state.getArea()[y][x] = state.getCurrent();
        Optional<String> winner = gamePlayValidation.hasWinner(state);
        if (winner.isPresent()) {
            state.setWinnerCoordinates(winner.get());
            state.setWinnerFigure(state.getCurrent());
            state.getStates().put(username, OX3State.WAITING);
        } else {
            state.setCurrent(Figure.O.equals(state.getCurrent()) ? Figure.X : Figure.O);
        }
        return gameStateRepository.update(state);
    }

    @Override
    public GameState checkState(String guid, String username) throws GameStateException {
        final GameState state = gameStateRepository.findById(guid);
        gameStateValidation.validatePlayActionsAllowed(state, username);
        if (gamePlayValidation.isGameOver(state)) {
            state.getStates().put(username, OX3State.WAITING);
            return gameStateRepository.update(state);
        }
        return state;
    }

    @Override
    public boolean canMove(GameState state, String username) throws GameStateException {
        return gamePlayValidation.checkTour(state, username);
    }

    @Override
    public boolean isWinner(GameState state, String username) throws GameStateException {
        return gamePlayValidation.isWinner(state, username);
    }

    @Autowired
    public void setGameStateRepository(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    @Autowired
    public void setGameStateValidation(GameStateValidation gameStateValidation) {
        this.gameStateValidation = gameStateValidation;
    }

    @Autowired
    public void setGamePlayValidation(GamePlayValidation gamePlayValidation) {
        this.gamePlayValidation = gamePlayValidation;
    }
}
