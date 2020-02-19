package gamehub.gamebind.repository.impl;

import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
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
    public List<GameBind> findAvailable() {
        return games.values()
                .stream()
                .filter(game -> GameBindStatus.OPEN.equals(game.getStatus()))
                .collect(Collectors.toList());
    }
}
