package gamehub.gamebind.service.impl;

import com.google.common.collect.Lists;
import gamehub.gamebind.config.AvailableGames;
import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.exception.GameCancelException;
import gamehub.gamebind.model.*;
import gamehub.gamebind.repository.GameBindRepository;
import gamehub.gamebind.strategy.CancelJoinerStrategy;
import gamehub.gamebind.strategy.CancelOwnerStrategy;
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
    private CancelOwnerStrategy cancelOwnerStrategy;
    @Mock
    private CancelJoinerStrategy cancelJoinerStrategy;

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
        when(insertGame.getType()).thenReturn(GameType.OX3);
        when(gameDef.getType()).thenReturn(GameType.OX3);
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
        thrown.expectMessage("Game type 'OX3' not found.");

        // when
        testObj.create(insertGame);
    }

    @Test
    public void shouldNotCreateCausedByNoMinEcpectedPlayers() throws GameBindException {

        // given
        when(availableGames.getGameByType(GameType.OX3)).thenReturn(Optional.of(gameDef));
        when(gameDef.getMinPlayers()).thenReturn(2);
        when(gameDef.getMaxPlayers()).thenReturn(4);
        when(insertGame.getExpectedPlayers()).thenReturn(1);
        thrown.expect(GameBindException.class);
        thrown.expectMessage("Player count bust be between 2 and 4");

        // when
        testObj.create(insertGame);
    }

    @Test
    public void shouldNotCreateCausedByNoMaxEcpectedPlayers() throws GameBindException {

        // given
        when(availableGames.getGameByType(GameType.OX3)).thenReturn(Optional.of(gameDef));
        when(gameDef.getMinPlayers()).thenReturn(2);
        when(gameDef.getMaxPlayers()).thenReturn(4);
        when(insertGame.getExpectedPlayers()).thenReturn(5);
        thrown.expect(GameBindException.class);
        thrown.expectMessage("Player count bust be between 2 and 4");

        // when
        testObj.create(insertGame);
    }

    @Test
    public void shouldCreate() throws GameBindException {

        // given
        when(availableGames.getGameByType(GameType.OX3)).thenReturn(Optional.of(gameDef));
        when(gameBindRepository.insert(any())).thenReturn(insertGame);
        when(gameDef.getMinPlayers()).thenReturn(2);
        when(gameDef.getMaxPlayers()).thenReturn(4);
        when(insertGame.getExpectedPlayers()).thenReturn(4);

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
        when(gameBindRepository.findAvailable(GameType.OX3)).thenReturn(Lists.newArrayList(findGame_3));

        // when
        List<GameBind> games = testObj.getAvailableGames(GameType.OX3);

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

    @Test
    public void shouldNotCancelCausedByClosedStatus() throws GameBindException, GameCancelException {

        // given
        when(joinGame.getStatus()).thenReturn(GameBindStatus.CLOSED);
        thrown.expect(GameCancelException.class);
        thrown.expectMessage("Unable to cancel closed or canceled game bind");

        // when
        testObj.cancel("join_guid", "user");
    }

    @Test
    public void shouldNotCancelCausedByCancelStatus() throws GameBindException, GameCancelException {

        // given
        when(joinGame.getStatus()).thenReturn(GameBindStatus.CANCELED);
        thrown.expect(GameCancelException.class);
        thrown.expectMessage("Unable to cancel closed or canceled game bind");

        // when
        testObj.cancel("join_guid", "user");
    }

    @Test
    public void shouldCancelByOwner() throws GameBindException, GameCancelException {

        // given
        when(joinGame.getStatus()).thenReturn(GameBindStatus.OPEN);
        when(joinGame.getOwner()).thenReturn(player);

        // when
        testObj.cancel("join_guid", "player");

        // then
        verify(cancelOwnerStrategy).cancel(joinGame, "player");
    }

    @Test
    public void shouldCancelByJoiner() throws GameCancelException, GameBindException {

        // given
        when(joinGame.getStatus()).thenReturn(GameBindStatus.OPEN);
        when(joinGame.getOwner()).thenReturn(player);

        // when
        testObj.cancel("join_guid", "other_player");

        // then
        verify(cancelJoinerStrategy).cancel(joinGame, "other_player");
    }
}