package gamehub.gamebind.strategy;

import gamehub.gamebind.exception.GameCancelException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CancelOwnerStrategyTest {

    @InjectMocks
    private CancelOwnerStrategy testObj;

    @Mock
    private GameBind bind;

    @Test
    public void shouldCancel() throws GameCancelException {

        // when
        testObj.cancel(bind, "player");

        // then
        verify(bind).setStatus(GameBindStatus.CANCELED);
    }
}