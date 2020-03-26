package gamehub.gamebind.converter;

import com.google.common.collect.Lists;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.Player;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import gamehub.sdk.enums.GameType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameBindConverterTest {

    @InjectMocks
    private GameBindConverter testOBj;

    @Mock
    private GameBind source;
    @Mock
    private Player owner, player;

    @Test
    public void shouldPopulate() {

        // given
        when(owner.getDisplayName()).thenReturn("_owner");
        when(player.getDisplayName()).thenReturn("_player");
        when(source.getGuid()).thenReturn("_guid");
        when(source.getType()).thenReturn(GameType.OX3);
        when(source.getOwner()).thenReturn(owner);
        when(source.getPlayers()).thenReturn(Lists.newArrayList(owner, player));

        // when
        final GameBindDTO bindDTO = testOBj.convert(source);


        // then
        assertThat(bindDTO).isNotNull();
        assertThat(bindDTO.getGuid()).isEqualTo("_guid");
        assertThat(bindDTO.getType()).isEqualTo("OX3");
        assertThat(bindDTO.getOwner()).isEqualTo("_owner");
        assertThat(bindDTO.getPlayers()).containsExactly("_owner","_player");
    }
}