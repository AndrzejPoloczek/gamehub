package gamehub.ox3.converter;

import gamehub.ox3.dto.ReadyDTO;
import gamehub.ox3.model.Figure;
import gamehub.ox3.model.GameState;
import gamehub.ox3.service.GameStateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StateToReadyConverterTest {

    @InjectMocks
    private StateToReadyConverter testObj;

    @Mock
    private GameStateService gameStateService;

    @Mock
    private GameState source;

    @Test
    public void shouldPopulateNotReadyState() {

        // given
        when(gameStateService.isReady(source)).thenReturn(false);

        // then
        ReadyDTO target = testObj.convert(source);

        // then
        assertThat(target).isNotNull();
        assertThat(target.isReady()).isFalse();
        assertThat(target.getArea()).isNull();
    }

    @Test
    public void shouldPopulateReadyState() {

        // given
        final Figure[][] area = new Figure[3][3];
        when(gameStateService.isReady(source)).thenReturn(true);
        when(source.getArea()).thenReturn(area);

        // then
        ReadyDTO target = testObj.convert(source);

        // then
        assertThat(target).isNotNull();
        assertThat(target.isReady()).isTrue();
        assertThat(target.getArea()).isEqualTo(area);
    }
}