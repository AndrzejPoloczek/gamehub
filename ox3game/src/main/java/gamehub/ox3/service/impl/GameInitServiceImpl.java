package gamehub.ox3.service.impl;

import gamehub.ox3.model.Figure;
import gamehub.ox3.model.OX3State;
import gamehub.ox3.service.GameInitService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameInitServiceImpl implements GameInitService {

    @Override
    public Figure[][] createArea() {
        Figure[][] area = new Figure[3][3];
        for (int x = 0; x < area.length; x++) {
            for (int y = 0; y < area[x].length; y++) {
                area[x][y] = Figure.EMPTY;
            }
        }
        return area;
    }

    @Override
    public Map<String, Figure> getInitialFigures(final List<String> usernames) {
        final Map<String, Figure> figures = new HashMap<>();
        figures.put(usernames.get(0), Figure.O);
        figures.put(usernames.get(1), Figure.X);
        return figures;
    }

    @Override
    public Map<String, OX3State> getInitialStates(final List<String> usernames) {
        final Map<String, OX3State> figures = new HashMap<>();
        figures.put(usernames.get(0), OX3State.WAITING);
        figures.put(usernames.get(1), OX3State.WAITING);
        return figures;
    }
}
