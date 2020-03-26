package gamehub.ox3.converter;

import com.google.common.collect.Lists;
import gamehub.sdk.dto.gamebind.PlayerDTO;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class InitialPlayersConverterTest {

    @InjectMocks
    private InitialPlayersConverter testObj;

    @Mock
    private GameCreateDTO source;
    @Mock
    private PlayerDTO player1, player2;

    @Test
    public void shouldPopulate() {

        // given
        when(player1.getUsername()).thenReturn("u1");
        when(player1.getDisplayName()).thenReturn("d1");
        when(player2.getUsername()).thenReturn("u2");
        when(player2.getDisplayName()).thenReturn("d2");
        when(source.getPlayers()).thenReturn(Lists.newArrayList(player1, player2));

        // when
        final Map<String, String> result = testObj.convert(source);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get("u1")).isEqualTo("d1");
        assertThat(result.get("u2")).isEqualTo("d2");
    }
}