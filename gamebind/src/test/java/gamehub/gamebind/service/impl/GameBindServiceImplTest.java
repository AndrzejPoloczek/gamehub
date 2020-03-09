package gamehub.gamebind.service.impl;

import com.google.common.collect.Lists;
import gamehub.gamebind.config.AvailableGames;
import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.*;
import gamehub.gamebind.repository.GameBindRepository;
import gamehub.sdk.enums.GameType;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameBindServiceImplTest {

    @InjectMocks
    private GameBindServiceImpl testObj;

    @Mock
    private AvailableGames availableGames;
    @Mock
    private GameBindRepository gameBindRepository;

    @Mock
    private GameDefinition gameDef;
    @Mock
    private Player player, otherPlayer;
    @Mock
    private GameBind insertGame, findGame_1, findGame_2, findGame_3, joinGame;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws GameBindException {
        when(gameBindRepository.findByGuid("join_guid")).thenReturn(joinGame);
        when(joinGame.getPlayers()).thenReturn(Lists.newArrayList(player));
        when(insertGame.getType()).thenReturn(GameType.SAMPLE_GAME);
        when(gameDef.getType()).thenReturn(GameType.SAMPLE_GAME);
        when(findGame_1.getStatus()).thenReturn(GameBindStatus.OPEN);
        when(findGame_2.getStatus()).thenReturn(GameBindStatus.OPEN);
        when(findGame_3.getStatus()).thenReturn(GameBindStatus.OPEN);
        when(player.getUsername()).thenReturn("player");
        when(otherPlayer.getUsername()).thenReturn("other_player");
    }

    @Test
    public void shouldNotCreateCausedByWrongType() throws GameBindException {

        // given
        thrown.expect(GameBindException.class);
        thrown.expectMessage("Game type 'SAMPLE_GAME' not found.");

        // when
        testObj.create(insertGame);
    }

    @Test
    public void shouldCreate() throws GameBindException {

        // given
        when(availableGames.getGameByType(GameType.SAMPLE_GAME)).thenReturn(Optional.of(gameDef));
        when(gameBindRepository.insert(any())).thenReturn(insertGame);

        // when
        GameBind game = testObj.create(insertGame);

        // then
        verify(gameBindRepository).insert(insertGame);
        Assertions.assertThat(game).isEqualTo(insertGame);
    }

    @Test
    public void shouldFindProperGames() {

        // given
        when(gameBindRepository.findAvailable()).thenReturn(Lists.newArrayList(findGame_1, findGame_2, findGame_3));

        // when
        List<GameBind> games = testObj.getAvailableGames();

        // then
        Assertions.assertThat(games).isNotNull();
        Assertions.assertThat(games.size()).isEqualTo(3);
        Assertions.assertThat(games.get(0)).isEqualTo(findGame_1);
        Assertions.assertThat(games.get(1)).isEqualTo(findGame_2);
        Assertions.assertThat(games.get(2)).isEqualTo(findGame_3);
    }

    @Test
    public void shouldFindProperGamesByType() {

        // given
        when(gameBindRepository.findAvailable(GameType.SAMPLE_GAME)).thenReturn(Lists.newArrayList(findGame_3));

        // when
        List<GameBind> games = testObj.getAvailableGames(GameType.SAMPLE_GAME);

        // then
        Assertions.assertThat(games).isNotNull();
        Assertions.assertThat(games.size()).isEqualTo(1);
        Assertions.assertThat(games.get(0)).isEqualTo(findGame_3);
    }

    @Test
    public void shouldJoinGame() throws GameBindException {

        // given
        when(gameBindRepository.join("guid", player)).thenReturn(insertGame);

        // when
        GameBind result = testObj.join("guid", player);

        // then
        verify(gameBindRepository).join("guid", player);
        Assertions.assertThat(result).isEqualTo(insertGame);
    }

    @Test
    public void shouldNotJoinGameCauseByException() throws GameBindException {

        // given
        when(gameBindRepository.join("guid", player)).thenThrow(new GameBindException("Some Exception"));
        thrown.expect(GameBindException.class);
        thrown.expectMessage("Some Exception");

        // when
        GameBind result = testObj.join("guid", player);
    }

    @Test
    public void shouldNotUpdatePlayerStatusCausedByUnknownPlayer() throws GameBindException {

        // given
        when(joinGame.getPlayers()).thenReturn(Lists.newArrayList(otherPlayer));
        thrown.expect(GameBindException.class);
        thrown.expectMessage("You are not allowed to update this game");

        // when
        testObj.updatePlayerStatus("join_guid", "player");
    }

    @Test
    public void shouldNotUpdatePlayerStatusCausedByBindIsNotReady() throws GameBindException {

        // given
        when(joinGame.getStatus()).thenReturn(GameBindStatus.OPEN);
        when(joinGame.getGamePlayGuid()).thenReturn("play_guid");

        // when
        testObj.updatePlayerStatus("join_guid", "player");

        // then
        verify(player, never()).setStatus(PlayerStatus.NOTIFIED);
    }

    @Test
    public void shouldNotUpdatePlayerStatusCausedByLackOfGamePlayGuid() throws GameBindException {

        // given
        when(joinGame.getStatus()).thenReturn(GameBindStatus.CLOSED);

        // when
        testObj.updatePlayerStatus("join_guid", "player");

        // then
        verify(player, never()).setStatus(PlayerStatus.NOTIFIED);
    }

    @Test
    public void shouldUpdatePlayerStatusWCausedByCanceledStatus() throws GameBindException {

        // given
        when(joinGame.getStatus()).thenReturn(GameBindStatus.CANCELED);
        when(joinGame.getGamePlayGuid()).thenReturn("play_guid");

        // when
        testObj.updatePlayerStatus("join_guid", "player");

        // then
        verify(player).setStatus(PlayerStatus.NOTIFIED);
    }

    @Test
    public void shouldUpdatePlayerStatusWCausedByClosedStatus() throws GameBindException {

        // given
        when(joinGame.getStatus()).thenReturn(GameBindStatus.CLOSED);
        when(joinGame.getGamePlayGuid()).thenReturn("play_guid");

        // when
        testObj.updatePlayerStatus("join_guid", "player");

        // then
        verify(player).setStatus(PlayerStatus.NOTIFIED);
    }
}