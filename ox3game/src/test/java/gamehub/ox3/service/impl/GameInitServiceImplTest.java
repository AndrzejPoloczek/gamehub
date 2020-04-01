package gamehub.ox3.service.impl;

import com.google.common.collect.Lists;
import gamehub.ox3.model.Figure;
import gamehub.ox3.model.OX3State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameInitServiceImplTest {

    @InjectMocks
    private GameInitServiceImpl testObj;

    @Test
    public void shouldCreateArea() {

        // when
        Figure[][] result = testObj.createArea();

        // then
        assertThat(result).isNotNull();
        assertThat(result.length).isEqualTo(3);
        for (int x = 0; x < result.length; x++) {
            assertThat(result[x].length).isEqualTo(3);
        }
        for (int x = 0; x < result.length; x++) {
            for (int y = 0; y < result[x].length; y++) {
                assertThat(result[x][y]).isEqualTo(Figure.EMPTY);
            }
        }
    }

    @Test
    public void shouleGetInitialFigures() {

        // when
        Map<String, Figure> result = testObj.getInitialFigures(Lists.newArrayList("un1", "un2"));

        // then
        assertThat(result).isNotNull();
        assertThat(result.containsKey("un1")).isTrue();
        assertThat(result.containsKey("un2")).isTrue();
        assertThat(result.get("un1")).isEqualTo(Figure.O);
        assertThat(result.get("un2")).isEqualTo(Figure.X);
    }

    @Test
    public void shouldGetInitialStates() {

        // when
        Map<String, OX3State> result = testObj.getInitialStates(Lists.newArrayList("un1", "un2"));

        // then
        assertThat(result).isNotNull();
        assertThat(result.containsKey("un1")).isTrue();
        assertThat(result.containsKey("un2")).isTrue();
        assertThat(result.get("un1")).isEqualTo(OX3State.WAITING);
        assertThat(result.get("un2")).isEqualTo(OX3State.WAITING);
    }
}