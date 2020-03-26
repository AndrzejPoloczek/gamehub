package gamehub.ox3.service.impl;

import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.Figure;
import gamehub.ox3.model.GameState;
import gamehub.ox3.model.OX3State;
import gamehub.ox3.repository.GameStateRepository;
import gamehub.ox3.service.GameStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameStateServiceImpl implements GameStateService {

    private GameStateRepository gameStateRepository;

    @Override
    public GameState create(Map<String, String> players) throws GameStateException {
        validatePlayers(players);
        final GameState state = new GameState();
        state.setPlayers(players);
        state.setFigures(getInitialFigures(players));
        state.setCurrent(Figure.O);
        state.setState(OX3State.WAITING);
        return gameStateRepository.create(state);
    }

    private Map<String, Figure> getInitialFigures(Map<String, String> players) {
        final Map<String, Figure> figures = new HashMap<>();
        final List<String> keys = players.keySet().stream().collect(Collectors.toList());
        figures.put(keys.get(0), Figure.O);
        figures.put(keys.get(1), Figure.X);
        return figures;
    }

    private void validatePlayers(Map<String, String> players) throws GameStateException {
        if (players.size() != 2) {
            throw new GameStateException("Wrong players count");
        }
    }

    @Autowired
    public void setGameStateRepository(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }
}
