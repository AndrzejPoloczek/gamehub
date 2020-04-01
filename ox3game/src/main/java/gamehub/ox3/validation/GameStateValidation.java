package gamehub.ox3.validation;

import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.GameState;
import gamehub.ox3.model.OX3State;
import org.springframework.stereotype.Component;

@Component
public class GameStateValidation {

    public void validateReadyActionsAllowed(final GameState state, final String username) throws GameStateException {
        validateUsername(state, username);
        if (!OX3State.WAITING.equals(state.getStates().get(username))) {
            throw new GameStateException("You can not perform this action in this state");
        }
    }

    public void validatePlayActionsAllowed(final GameState state, final String username) throws GameStateException {
        validateUsername(state, username);
        if (!OX3State.PLAY.equals(state.getStates().get(username))) {
            throw new GameStateException("You can not perform this action in this state");
        }
    }

    public void validateUsername(final GameState state, final String username) throws GameStateException {
        if (!state.getStates().keySet().contains(username)) {
            throw new GameStateException(String.format("No user %s found in this game", username));
        }
    }
}
