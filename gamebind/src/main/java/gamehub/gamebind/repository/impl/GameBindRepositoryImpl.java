package gamehub.gamebind.repository.impl;

import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.gamebind.model.Player;
import gamehub.gamebind.model.PlayerStatus;
import gamehub.gamebind.repository.GameBindRepository;
import gamehub.sdk.enums.GameType;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GameBindRepositoryImpl implements GameBindRepository {

    private Map<String, GameBind> games;

    @PostConstruct
    public void init() {
        games = new HashMap<>();
    }

    @Override
    public GameBind insert(GameBind gameBind) {
        gameBind.setGuid(UUID.randomUUID().toString());
        games.put(gameBind.getGuid(), gameBind);
        return gameBind;
    }

    @Override
    public GameBind join(String guid, Player player) throws GameBindException {
        final GameBind game = findByGuid(guid);
        synchronized (game) {
            if (!canJoin(game)) {
                throw new GameBindException("Game is not available anomore");
            }
            game.getPlayers().add(player);
        }
        return game;
    }

    @Override
    public GameBind findByGuid(String guid) throws GameBindException {
        if (!games.containsKey(guid)) {
            throw new GameBindException("Unable to find game with id " + guid);
        }
        return games.get(guid);
    }

    @Override
    public List<GameBind> findAvailable() {
        return games.values()
                .stream()
                .filter(this::canJoin)
                .collect(Collectors.toList());
    }

    @Override
    public List<GameBind> findAvailable(GameType type) {
        return games.values()
                .stream()
                .filter(game -> canJoin(game, type))
                .collect(Collectors.toList());
    }

    @Override
    public void update(String guid, Optional<GameBindStatus> newStatus, Optional<String> gamePlayGuid) throws GameBindException {
        final GameBind game = findByGuid(guid);
        synchronized (game) {
            newStatus.ifPresent(game::setStatus);
            gamePlayGuid.ifPresent(game::setGamePlayGuid);
        }
    }

    @Override
    public Set<String> findGamesToRemove() {
        return games.values().stream()
                .filter(this::shouldRemove)
                .map(GameBind::getGuid)
                .collect(Collectors.toSet());
    }

    @Override
    public void remove(String guid) {
        games.remove(guid);
    }

    private boolean shouldRemove(final GameBind gameBind) {
        return GameBindStatus.OPEN.equals(gameBind.getStatus()) && gameBind.getPlayers().stream()
                        .allMatch(player -> PlayerStatus.NOTIFIED.equals(player.getStatus()));
    }

    private boolean canJoin(GameBind game) {
        return GameBindStatus.OPEN.equals(game.getStatus()) && game.getExpectedPlayers() > game.getPlayers().size();
    }

    private boolean canJoin(GameBind game, GameType type) {
        return canJoin(game) && type.equals(game.getType());
    }
}
