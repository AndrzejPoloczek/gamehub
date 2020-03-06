package gamehub.gameplay.repository.impl;

import gamehub.gameplay.model.GamePlay;
import gamehub.gameplay.repository.GamePlayRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GamePlayRepositoryImpl implements GamePlayRepository {

    private Map<String, GamePlay> games;

    @PostConstruct
    public void init() {
        games = new HashMap<>();
    }

    @Override
    public GamePlay insert(GamePlay gamePlay) {
        gamePlay.setGuid(UUID.randomUUID().toString());
        games.put(gamePlay.getGuid(), gamePlay);
        return gamePlay;
    }

    @Override
    public Optional<GamePlay> get(String guid) {
        return Optional.ofNullable(games.get(guid));
    }
}
