package gamehub.gamebind.converter;

import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.sdk.dto.gamebind.GameBindCheckDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class BindCheckConverterTest {

    @InjectMocks
    private BindCheckConverter testObj;

    @Mock
    private GameBind source;

    @Test
    public void shouldConvertToReady() {

        // given
        when(source.getStatus()).thenReturn(GameBindStatus.CLOSED);
        when(source.getGamePlayGuid()).thenReturn("play_guid");

        // when
        GameBindCheckDTO check = testObj.convert(source);

        // then
        assertThat(check.isReady()).isTrue();
        assertThat(check.isCanceled()).isFalse();
        assertThat(check.getPlayGuid()).isEqualTo("play_guid");
    }

    @Test
    public void shouldConvertToNotReadyCauseByOpenStatus() {

        // given
        when(source.getStatus()).thenReturn(GameBindStatus.OPEN);
        when(source.getGamePlayGuid()).thenReturn("play_guid");

        // when
        GameBindCheckDTO check = testObj.convert(source);

        // then
        assertThat(check.isReady()).isFalse();
        assertThat(check.isCanceled()).isFalse();
        assertThat(check.getPlayGuid()).isNull();
    }

    @Test
    public void shouldConvertToNotReadyCauseByNoPlayGuid() {

        // given
        when(source.getStatus()).thenReturn(GameBindStatus.CLOSED);

        // when
        GameBindCheckDTO check = testObj.convert(source);

        // then
        assertThat(check.isReady()).isFalse();
        assertThat(check.isCanceled()).isFalse();
        assertThat(check.getPlayGuid()).isNull();
    }

    @Test
    public void shouldConvertTCanceled() {

        // given
        when(source.getStatus()).thenReturn(GameBindStatus.CANCELED);
        when(source.getGamePlayGuid()).thenReturn("play_guid");

        // when
        GameBindCheckDTO check = testObj.convert(source);

        // then
        assertThat(check.isReady()).isFalse();
        assertThat(check.isCanceled()).isTrue();
        assertThat(check.getPlayGuid()).isNull();
    }
}