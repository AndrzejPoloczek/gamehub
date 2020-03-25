package gamehub.gamebind.service.impl;

import gamehub.gamebind.component.GamePlayGuidRetryable;
import gamehub.gamebind.config.AvailableGames;
import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.exception.GamePlayGuidException;
import gamehub.gamebind.model.*;
import gamehub.gamebind.repository.GameBindRepository;
import gamehub.gamebind.service.GameBindService;
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


    @Override
    public GameBind create(final GameBind gameBind) throws GameBindException {
        validateGameType(gameBind.getType());
        final GameBind created = gameBindRepository.insert(gameBind);
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
        final GameBind gameBind = gameBindRepository.join(guid, player);
        handleBindFilled(gameBind);
        return gameBind;
    }

    @Override
    public GameBind updatePlayerStatus(String guid, String username) throws GameBindException {
        final GameBind gameBind = gameBindRepository.findByGuid(guid);
        final Player player = gameBind.getPlayers()
                .stream()
                .filter(current -> current.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new GameBindException("You are not allowed to update this game"));
        if (shouldNotify(gameBind)) {
            player.setStatus(PlayerStatus.NOTIFIED);
        }
        return gameBind;
    }

    private boolean shouldNotify(final GameBind gameBind) {
        return (GameBindStatus.CLOSED.equals(gameBind.getStatus()) && StringUtils.isNotBlank(gameBind.getGamePlayGuid()))
                || GameBindStatus.CANCELED.equals(gameBind.getStatus());
    }

    private void handleBindFilled(final GameBind gameBind) throws GameBindException {
        if (gameBind.getExpectedPlayers() == gameBind.getPlayers().size()) {
            gameBindRepository.update(gameBind.getGuid(), Optional.of(GameBindStatus.CLOSED), Optional.empty());
            Executors.newSingleThreadExecutor().execute(() -> updateGamePlayGuid(gameBind.getGuid(), findPlayGuid(gameBind)));
        }
    }

    private Optional<String> findPlayGuid(final GameBind gameBind) {
        try {
            return Optional.of(gamePlayGuidRetryable.getGuid(gameBind));
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

    private void validateGameType(GameType type) throws GameBindException {
        if (!availableGames.getGameByType(type).isPresent()) {
            throw new GameBindException(String.format("Game type '%s' not found.", type));
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
}
