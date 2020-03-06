package gamehub.gamebind.component;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import gamehub.gamebind.clients.GamePlayClient;
import gamehub.gamebind.exception.GamePlayGuidException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.converter.PlayerReverseConverter;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GamePlayGuidRetryableTest {

    @InjectMocks
    private GamePlayGuidRetryable testObj;

    @Mock
    private GamePlayClient gamePlayClient;
    @Mock
    private PlayerReverseConverter playerReversePopulator;

    @Mock
    private GameBind gameBind;
    @Mock
    private ResponseEntity ok, failed;


    @Before
    public void setUp() {
        when(ok.getStatusCode()).thenReturn(HttpStatus.OK);
        when(ok.getBody()).thenReturn("GUID");
        when(failed.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldGetGuid() throws GamePlayGuidException {

        // given
        when(gamePlayClient.create(any())).thenReturn(ok);

        // when
        final String guid = testObj.getGuid(gameBind);

        // then
        Assertions.assertThat(guid).isNotBlank();
        Assertions.assertThat(guid).isEqualTo("GUID");
    }

    @Test(expected = GamePlayGuidException.class)
    public void shouldNotGetGuidCauseOfGamePlayGuidException() throws GamePlayGuidException {

        // given
        when(gamePlayClient.create(any())).thenReturn(failed);

        // when
        testObj.getGuid(gameBind);
    }

    @Test(expected = HystrixBadRequestException.class)
    public void shouldNotGetGuidCauseOfHystrixBadRequestException() throws GamePlayGuidException {

        // given
        when(gamePlayClient.create(any())).thenThrow(new HystrixBadRequestException("failed"));

        // when
        testObj.getGuid(gameBind);
    }
}