package gamehub.ox3.converter;

import gamehub.ox3.dto.PlayDTO;
import gamehub.ox3.model.Figure;
import gamehub.ox3.model.GameState;
import gamehub.ox3.validation.GamePlayValidation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StateToMoveConverterTest {

    private static final String WINNER_COORDINATES = "coordinates";

    @InjectMocks
    private StateToMoveConverter testObj;

    @Mock
    private GamePlayValidation gamePlayValidation;;
    @Mock
    private GameState source;

    private Figure[][] area;

    @Before
    public void setUp() {
        when(source.getArea()).thenReturn(area);
    }

    @Test
    public void shouldPopulateWithoutGameOver() {

        // given
        when(gamePlayValidation.isGameOver(source)).thenReturn(false);

        // when
        PlayDTO result = testObj.convert(source);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getArea()).isEqualTo(area);
        assertThat(result.isOver()).isFalse();
        assertThat(result.isWinner()).isFalse();
        assertThat(result.getWinnerCoordinates()).isNull();
    }

    @Test
    public void shouldPopulateWithGameOver() {

//        // given
//        when(gamePlayValidation.isGameOver(source)).thenReturn(true);
//
//        // when
//        PlayDTO result = testObj.convert(source);
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result.getArea()).isEqualTo(area);
//        assertThat(result.isOver()).isTrue();
//        assertThat(result.isWinner()).isFalse();
//        assertThat(result.getWinnerCoordinates()).isNull();
    }
}