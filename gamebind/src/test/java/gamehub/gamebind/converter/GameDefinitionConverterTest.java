package gamehub.gamebind.converter;

import gamehub.gamebind.model.GameDefinition;
import gamehub.sdk.dto.gamebind.GameDefinitionDTO;
import gamehub.sdk.enums.GameType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameDefinitionConverterTest {

    @InjectMocks
    private GameDefinitionConverter testObbj;

    @Mock
    private GameDefinition source;

    @Test
    public void shouldPopulate() {

        // given
        when(source.getType()).thenReturn(GameType.SAMPLE_GAME);
        when(source.getName()).thenReturn("_name");
        when(source.getDescription()).thenReturn("_description");
        when(source.getMinPlayers()).thenReturn(2);
        when(source.getMaxPlayers()).thenReturn(5);

        // when
        final GameDefinitionDTO target =  testObbj.convert(source);

        // then
        assertThat(target).isNotNull();
        assertThat(target.getType()).isEqualTo(GameType.SAMPLE_GAME);
        assertThat(target.getName()).isEqualTo("_name");
        assertThat(target.getDescription()).isEqualTo("_description");
        assertThat(target.getMinPlayers()).isEqualTo(2);
        assertThat(target.getMaxPlayers()).isEqualTo(5);
    }
}