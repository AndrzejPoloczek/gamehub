package gamehub.gameplay.converters;

import com.google.common.collect.Lists;
import gamehub.gameplay.model.GamePlay;
import gamehub.gameplay.model.Player;
import gamehub.sdk.dto.gamebind.PlayerDTO;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import gamehub.sdk.enums.GameType;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameCreateToGamePlayConverterTest {

    @InjectMocks
    private GameCreateToGamePlayConverter testObj;

    @Mock
    private PlayerConverter playerConverter;

    @Mock
    private GameCreateDTO gameCreateDTO;
    @Mock
    private PlayerDTO dto1, dto2;
    @Mock
    private Player player1, player2;


    @Test
    public void shouldPopulate() {

        // given
        when(playerConverter.convert(dto1)).thenReturn(player1);
        when(playerConverter.convert(dto2)).thenReturn(player2);
        when(gameCreateDTO.getPlayers()).thenReturn(Lists.newArrayList(dto1, dto2));
        when(gameCreateDTO.getGameType()).thenReturn(GameType.SAMPLE_GAME);

        // when
        final GamePlay gamePlay = testObj.convert(gameCreateDTO);

        // then
        Assertions.assertThat(gamePlay).isNotNull();
        Assertions.assertThat(gamePlay.getType()).isEqualTo(GameType.SAMPLE_GAME);
        Assertions.assertThat(gamePlay.getPlayers()).isNotNull();
        Assertions.assertThat(gamePlay.getPlayers()).containsExactly(player1, player2);
    }
}