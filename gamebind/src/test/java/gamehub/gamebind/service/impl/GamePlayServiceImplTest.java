package gamehub.gamebind.service.impl;

import gamehub.gamebind.clients.GameClient;
import gamehub.gamebind.clients.OX3GameClient;
import gamehub.sdk.enums.GameType;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class GamePlayServiceImplTest {

    @InjectMocks
    private GamePlayServiceImpl testObj;

    @Mock
    private OX3GameClient ox3GameClient;

    @Test
    public void shouldGetOX3GameClient() {

        // when
        final Optional<GameClient> client = testObj.getClient(GameType.OX3);

        // then
        Assertions.assertThat(client.isPresent()).isTrue();
        Assertions.assertThat(client.get()).isEqualTo(ox3GameClient);
    }

    @Test
    public void shouldGetEmpty() {

        // when
        final Optional<GameClient> client = testObj.getClient(GameType.INVALID_TYPE);

        // then
        Assertions.assertThat(client.isPresent()).isFalse();
    }
}