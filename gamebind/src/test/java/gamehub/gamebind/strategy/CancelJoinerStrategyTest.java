package gamehub.gamebind.strategy;

import com.google.common.collect.Lists;
import gamehub.gamebind.exception.GameCancelException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.Player;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CancelJoinerStrategyTest {

    @InjectMocks
    private CancelJoinerStrategy testObj;

    @Mock
    private GameBind bind;
    @Mock
    private Player player;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        when(bind.getPlayers()).thenReturn(Lists.newArrayList(player));
    }

    @Test
    public void shouldNotCancelCauseByInvalidPlayer() throws GameCancelException {

        // given
        when(player.getUsername()).thenReturn("wrong_user");
        thrown.expect(GameCancelException.class);
        thrown.expectMessage("You are not allowed to cancel this game");

        // when
        testObj.cancel(bind, "player");
    }

    @Test
    public void shouldCancel() throws GameCancelException {

        // given
        when(player.getUsername()).thenReturn("player");

        // when
        testObj.cancel(bind, "player");

        // then
        Assertions.assertThat(bind.getPlayers()).isEmpty();
    }
}