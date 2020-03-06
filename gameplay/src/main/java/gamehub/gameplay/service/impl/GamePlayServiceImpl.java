package gamehub.gameplay.service.impl;

import gamehub.gameplay.model.GamePlay;
import gamehub.gameplay.repository.GamePlayRepository;
import gamehub.gameplay.service.GamePlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GamePlayServiceImpl implements GamePlayService {

    private GamePlayRepository gamePlayRepository;

    @Override
    public GamePlay create(final GamePlay gamePlay)  {
        return gamePlayRepository.insert(gamePlay);
    }

    @Override
    public Optional<GamePlay> get(String guid) {
        return gamePlayRepository.get(guid);
    }

    @Autowired
    public void setGamePlayRepository(GamePlayRepository gamePlayRepository) {
        this.gamePlayRepository = gamePlayRepository;
    }
}
