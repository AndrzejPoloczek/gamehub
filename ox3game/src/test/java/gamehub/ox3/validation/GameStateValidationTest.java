package gamehub.ox3.validation;

import gamehub.ox3.exception.GameStateException;
import gamehub.ox3.model.GameState;
import gamehub.ox3.model.OX3State;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameStateValidationTest {

    private static final String USER_1 = "u1";
    private static final String USER_2 = "u2";
    private static final String USER_3 = "u3";

    @InjectMocks
    private GameStateValidation testObj;

    @Mock
    private GameState state;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        when(state.getStates()).thenReturn(getMapWithStates(OX3State.WAITING));
    }

    @Test
    public void shouldValidateReadyActionsAllowed() throws GameStateException {

        // when
        testObj.validateReadyActionsAllowed(state, USER_1);
    }

    @Test
    public void shouldNotValidateReadyActionsAllowed() throws GameStateException {

        //given
        when(state.getStates()).thenReturn(getMapWithStates(OX3State.PLAY));
        expected.expect(GameStateException.class);
        expected.expectMessage("You can not perform this action in this state");

        testObj.validateReadyActionsAllowed(state, USER_1);
    }

    @Test
    public void shouldValidatePlayActionsAllowed() throws GameStateException {

        //given
        when(state.getStates()).thenReturn(getMapWithStates(OX3State.PLAY));

        // when
        testObj.validatePlayActionsAllowed(state, USER_2);
    }

    @Test
    public void shouldNotValidatePlayActionsAllowed() throws GameStateException {

        //given
        expected.expect(GameStateException.class);
        expected.expectMessage("You can not perform this action in this state");

        // when
        testObj.validatePlayActionsAllowed(state, USER_2);
    }

    @Test
    public void shouldValidateUsername() throws GameStateException {

        // when
        testObj.validateUsername(state, USER_1);
    }

    @Test
    public void shouldNotValidateUsername() throws GameStateException {

        // given
        expected.expect(GameStateException.class);
        expected.expectMessage("No user u3 found in this game");

        // when
        testObj.validateUsername(state, USER_3);
    }

    private Map<String, OX3State> getMapWithStates(OX3State state) {
        Map<String, OX3State> states = new HashMap<>();
        states.put(USER_1, state);
        states.put(USER_2, state);
        return states;
    }
}