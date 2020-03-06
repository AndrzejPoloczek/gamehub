package gamehub.gameplay.converters;

import gamehub.gameplay.model.Player;
import gamehub.sdk.dto.gamebind.PlayerDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class PlayerConverterTest {

    @InjectMocks
    private PlayerConverter testObj;

    @Mock
    private PlayerDTO dto;

    @Test
    public void shouldPopulate() {

        // given
        when(dto.getUsername()).thenReturn("_username");
        when(dto.getDisplayName()).thenReturn("_display_name");

        // when
        Player player = testObj.convert(dto);

        // then
        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getUsername()).isEqualTo("_username");
        Assertions.assertThat(player.getDisplayName()).isEqualTo("_display_name");
    }
}