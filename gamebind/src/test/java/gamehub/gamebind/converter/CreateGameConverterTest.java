package gamehub.gamebind.converter;

import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.gamebind.model.Player;
import gamehub.sdk.dto.gamebind.GameCreateDTO;
import gamehub.sdk.enums.GameType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreateGameConverterTest {

    @InjectMocks
    private CreateGameConverter testObj;

    @Mock
    private PlayerConverter playerConverter;

    @Mock
    private GameCreateDTO source;
    @Mock
    private Player player;


    @Test
    public void shouldPopulate() {

        // given
        when(playerConverter.convert(source)).thenReturn(player);
        when(source.getType()).thenReturn("OX3");
        when(source.getUsername()).thenReturn("_username");
        when(source.getDisplayName()).thenReturn("_displayName");
        when(source.getPlayers()).thenReturn(3);

        // when
        final GameBind gameBind = testObj.convert(source);

        // then
        assertThat(gameBind).isNotNull();
        assertThat(gameBind.getGuid()).isBlank();
        assertThat(gameBind.getExpectedPlayers()).isEqualTo(3);
        assertThat(gameBind.getPlayers()).containsOnly(player);
        assertThat(gameBind.getStatus()).isEqualTo(GameBindStatus.OPEN);
        assertThat(gameBind.getOwner()).isEqualTo(player);
        assertThat(gameBind.getType()).isEqualTo(GameType.OX3);
        assertThat(gameBind.getGamePlayGuid()).isBlank();
    }
}