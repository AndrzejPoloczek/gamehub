package gamehub.gamebind.repository.impl;

import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.gamebind.model.GameType;
import gamehub.gamebind.model.Player;
import gamehub.gamebind.repository.GameBindRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            if (game.getExpectedPlayers() == game.getPlayers().size()) {
                game.setStatus(GameBindStatus.CLOSED);
            }
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

    private boolean canJoin(GameBind game) {
        return GameBindStatus.OPEN.equals(game.getStatus()) && game.getExpectedPlayers() > game.getPlayers().size();
    }

    private boolean canJoin(GameBind game, GameType type) {
        return canJoin(game) && type.equals(game.getType());
    }
}
