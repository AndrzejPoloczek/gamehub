package gamehub.ox3.validation;

import gamehub.ox3.exception.GamePlayException;
import gamehub.ox3.model.Figure;
import gamehub.ox3.model.GameState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GamePlayValidation {

    private static final String PATTERN = "%s,%s;%s,%s;%s,%s";

    public void validateMove(final GameState state, final int x, final int y) throws GamePlayException {
        final Figure[][] area = state.getArea();
        if (y < 0 || area.length <= y) {
            throw new GamePlayException("Coordinate y is out of area range");
        }
        if (x < 0 || area[y].length <= x) {
            throw new GamePlayException("Coordinate x is out of area range");
        }
        if (!Figure.EMPTY.equals(area[y][x])) {
            throw new GamePlayException("Current area field is not empty");
        }
    }

    public boolean checkTour(final GameState state, final String username) {
        return state.getFigures().get(username).equals(state.getCurrent());
    }

    public boolean isWinner(final GameState state, final String username) {
        return state.getFigures().get(username).equals(state.getWinnerFigure());
    }

    public void validateTour(final GameState state, final String username) throws GamePlayException {
        if (!checkTour(state, username)) {
            throw new GamePlayException("It is not your tour");
        }
    }

    public Optional<String> hasWinner(final GameState state) {
        final Figure[][] area = state.getArea();
        for (int i = 0; i < area.length; i++) {
            if (!area[i][0].equals(Figure.EMPTY) && area[i][0] == area[i][1] && area[i][0]  == area[i][2]) {
                return Optional.of(String.format(PATTERN, i, 0, i, 1, i, 2));
            }
        }
        for (int i = 0; i < area[0].length; i++) {
            if (!area[0][1].equals(Figure.EMPTY) && area[0][i] == area[1][i] && area[0][i]  == area[2][i]) {
                return Optional.of(String.format(PATTERN, 0, i, 1, i, 2, 1));
            }
        }
        if (!area[0][0].equals(Figure.EMPTY) && area[0][0] == area[1][1] && area[0][0] == area[2][2]) {
            return Optional.of(String.format(PATTERN, 0, 0, 1, 1, 2, 2));
        }
        if (!area[2][0].equals(Figure.EMPTY) && area[2][0] == area[1][1] && area[2][0] == area[0][2]) {
            return Optional.of(String.format(PATTERN, 2, 0, 1, 1, 0, 2));
        }
        return Optional.empty();
    }

    public boolean isGameOver(final GameState state) {
        return StringUtils.isNotBlank(state.getWinnerCoordinates());
    }
}
