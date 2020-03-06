package gamehub.gamebind.converter;

import gamehub.gamebind.model.Player;
import gamehub.sdk.dto.gamebind.PlayerDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class PlayerConverterTest {

    @InjectMocks
    private PlayerConverter testOBj;

    @Mock
    private PlayerDTO source;

    @Test
    public void shouldPopulate() {

        // given
        when(source.getUsername()).thenReturn("_username");
        when(source.getDisplayName()).thenReturn("_displayName");

        // when
        final Player player = testOBj.convert(source);

        // then
        assertThat(player).isNotNull();
        assertThat(player.getUsername()).isEqualTo("_username");
        assertThat(player.getDisplayName()).isEqualTo("_displayName");
    }
}