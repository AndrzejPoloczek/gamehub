package gamehub.gamebind.service.impl;

import gamehub.gamebind.component.GamePlayGuidRetryable;
import gamehub.gamebind.config.AvailableGames;
import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.exception.GameCancelException;
import gamehub.gamebind.exception.GamePlayGuidException;
import gamehub.gamebind.model.*;
import gamehub.gamebind.repository.GameBindRepository;
import gamehub.gamebind.service.GameBindService;
import gamehub.gamebind.strategy.CancelJoinerStrategy;
import gamehub.gamebind.strategy.CancelOwnerStrategy;
import gamehub.gamebind.strategy.CancelStrategy;
import gamehub.sdk.enums.GameType;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executors;


@Service
public class GameBindServiceImpl implements GameBindService {

    private static final Logger LOG = LogManager.getLogger(GameBindServiceImpl.class);

    @Resource
    private AvailableGames availableGames;

    private GameBindRepository gameBindRepository;
    private GamePlayGuidRetryable gamePlayGuidRetryable;
    private CancelOwnerStrategy cancelOwnerStrategy;
    private CancelJoinerStrategy cancelJoinerStrategy;

    @Override
    public GameBind create(final GameBind bind) throws GameBindException {
        validateGameBind(bind);
        final GameBind created = gameBindRepository.insert(bind);
        handleBindFilled(created);
        return created;
    }

    @Override
    public List<GameBind> getAvailableGames() {
        return gameBindRepository.findAvailable();
    }

    @Override
    public List<GameBind> getAvailableGames(GameType type) {
        return gameBindRepository.findAvailable(type);
    }

    @Override
    public GameBind join(String guid, Player player) throws GameBindException {
        final GameBind bind = gameBindRepository.join(guid, player);
        handleBindFilled(bind);
        return bind;
    }

    @Override
    public GameBind updatePlayerStatus(String guid, String username) throws GameBindException {
        final GameBind bind = gameBindRepository.findByGuid(guid);
        final Player player = bind.getPlayers()
                .stream()
                .filter(current -> current.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new GameBindException("You are not allowed to update this game"));
        if (shouldNotify(bind)) {
            player.setStatus(PlayerStatus.NOTIFIED);
        }
        return bind;
    }

    @Override
    public void cancel(String guid, String username) throws GameBindException, GameCancelException {
        final GameBind bind = gameBindRepository.findByGuid(guid);
        if (!GameBindStatus.OPEN.equals(bind.getStatus())) {
            throw new GameCancelException("Unable to cancel closed or canceled game bind");
        }
        final CancelStrategy cancelStrategy = bind.getOwner().getUsername().equals(username)
                ? cancelOwnerStrategy : cancelJoinerStrategy;
        cancelStrategy.cancel(bind, username);
    }

    private boolean shouldNotify(final GameBind bind) {
        return (GameBindStatus.CLOSED.equals(bind.getStatus()) && StringUtils.isNotBlank(bind.getGamePlayGuid()))
                || GameBindStatus.CANCELED.equals(bind.getStatus());
    }

    private void handleBindFilled(final GameBind bind) throws GameBindException {
        if (bind.getExpectedPlayers() == bind.getPlayers().size()) {
            gameBindRepository.update(bind.getGuid(), Optional.of(GameBindStatus.CLOSED), Optional.empty());
            Executors.newSingleThreadExecutor().execute(() -> updateGamePlayGuid(bind.getGuid(), findPlayGuid(bind)));
        }
    }

    private Optional<String> findPlayGuid(final GameBind bind) {
        try {
            return Optional.of(gamePlayGuidRetryable.getGuid(bind));
        } catch (GamePlayGuidException | GameBindException e) {
            return Optional.empty();
        }
    }

    private void updateGamePlayGuid(final String guid, final Optional<String> gamePlayGuid) {
        try {
            if (gamePlayGuid.isPresent()) {
                gameBindRepository.update(guid, Optional.empty(), gamePlayGuid);
            } else {
                gameBindRepository.update(guid, Optional.of(GameBindStatus.CANCELED), Optional.empty());
            }
        } catch (GameBindException e) {
            LOG.error(String.format("Unable to update game play guid for game bind %s", guid), e);
        }
    }

    private void validateGameBind(final GameBind bind) throws GameBindException {
        final Optional<GameDefinition> gameOpt = availableGames.getGameByType(bind.getType());
        if (!gameOpt.isPresent()) {
            throw new GameBindException(String.format("Game type '%s' not found.", bind.getType()));
        }
        GameDefinition game = gameOpt.get();
        if (game.getMinPlayers() > bind.getExpectedPlayers() || game.getMaxPlayers() < bind.getExpectedPlayers()) {
            throw new GameBindException(String.format("Player count bust be between %s and %s",
                    game.getMinPlayers(), game.getMaxPlayers()));
        }
    }


    @Autowired
    public void setGameBindRepository(GameBindRepository gameBindRepository) {
        this.gameBindRepository = gameBindRepository;
    }

    @Autowired
    public void setGamePlayGuidRetryable(GamePlayGuidRetryable gamePlayGuidRetryable) {
        this.gamePlayGuidRetryable = gamePlayGuidRetryable;
    }

    @Autowired
    public void setCancelOwnerStrategy(CancelOwnerStrategy cancelOwnerStrategy) {
        this.cancelOwnerStrategy = cancelOwnerStrategy;
    }

    @Autowired
    public void setCancelJoinerStrategy(CancelJoinerStrategy cancelJoinerStrategy) {
        this.cancelJoinerStrategy = cancelJoinerStrategy;
    }
}
