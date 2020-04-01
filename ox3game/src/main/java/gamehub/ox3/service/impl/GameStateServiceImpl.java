package gamehub.ox3.service.impl;

import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.Figure;
import gamehub.ox3.model.GameState;
import gamehub.ox3.model.OX3State;
import gamehub.ox3.repository.GameStateRepository;
import gamehub.ox3.service.GameInitService;
import gamehub.ox3.service.GameStateService;
import gamehub.ox3.validation.GameStateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameStateServiceImpl implements GameStateService {

    private GameStateRepository gameStateRepository;
    private GameInitService gameInitService;
    private GameStateValidation gameStateValidation;

    @Override
    public GameState create(Map<String, String> players) throws GameStateException {
        validatePlayers(players);
        final GameState state = new GameState();
        final List<String> usernames = players.keySet().stream().collect(Collectors.toList());
        state.setPlayers(players);
        state.setFigures(gameInitService.getInitialFigures(usernames));
        state.setCurrent(Figure.O);
        state.setStates(gameInitService.getInitialStates(usernames));
        return gameStateRepository.create(state);
    }

    private void validatePlayers(Map<String, String> players) throws GameStateException {
        if (players.size() != 2) {
            throw new GameStateException("Wrong players count");
        }
    }

    @Override
    public GameState get(String guid, String username) throws GameStateException {
        final GameState state = gameStateRepository.findById(guid);
        gameStateValidation.validateUsername(state, username);
        return state;
    }

    @Override
    public GameState confirmReady(final String guid, final String username) throws GameStateException {
        final GameState state = gameStateRepository.findById(guid);
        gameStateValidation.validateReadyActionsAllowed(state, username);
        state.getStates().remove(username);
        state.getStates().put(username, OX3State.PLAY);
        if (isReady(state)) {
            state.setArea(gameInitService.createArea());
        }
        return gameStateRepository.update(state);
    }

    @Override
    public boolean isReady(String guid, String username) throws GameStateException {
        final GameState state = gameStateRepository.findById(guid);
        gameStateValidation.validateUsername(state, username);
        return isReady(state);
    }

    @Override
    public boolean isReady(GameState state) {
        return !state.getStates().values().contains(OX3State.WAITING);
    }

    @Autowired
    public void setGameStateRepository(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    @Autowired
    public void setGameInitService(GameInitService gameInitService) {
        this.gameInitService = gameInitService;
    }

    @Autowired
    public void setGameStateValidation(GameStateValidation gameStateValidation) {
        this.gameStateValidation = gameStateValidation;
    }
}
