package gamehub.ox3.service;

import gamehub.ox3.model.Figure;
import gamehub.ox3.model.OX3State;

import java.util.List;
import java.util.Map;

public interface GameInitService {

    /**
     * Create a new area.
     * @return
     */
    Figure[][] createArea();

    /**
     * Create an initial figures
     * @param usernames
     * @return
     */
    Map<String, Figure> getInitialFigures(final List<String> usernames);

    /**
     * Create an initial players states
     * @param usernames
     * @return
     */
    Map<String, OX3State> getInitialStates(final List<String> usernames);
}
