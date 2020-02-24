package gamehub.gamebind.service.impl;

import gamehub.gamebind.config.AvailableGames;
import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.*;
import gamehub.gamebind.repository.GameBindRepository;
import gamehub.gamebind.service.GameBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class GameBindServiceImpl implements GameBindService {

    @Resource
    private AvailableGames availableGames;

    private GameBindRepository gameBindRepository;


    @Override
    public GameBind create(GameType type, Player creator, int players) throws GameBindException {
        validateGameType(type);
        List<Player> playersList = new ArrayList<>();
        playersList.add(creator);
        GameBind gameBind = new GameBind();
        gameBind.setGuid(UUID.randomUUID().toString());
        gameBind.setType(type);
        gameBind.setOwner(creator);
        gameBind.setExpectedPlayers(players);
        gameBind.setStatus(GameBindStatus.OPEN);
        gameBind.setPlayers(playersList);
        return gameBindRepository.insert(gameBind);
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
        return gameBindRepository.join(guid, player);
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
}
