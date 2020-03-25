package gamehub.gamebind.service;

import gamehub.gamebind.clients.GameClient;
import gamehub.sdk.enums.GameType;

import java.util.Optional;

public interface GamePlayService {

    /**
     * Getting specified feign client for specified game type
     * @param type Game tye
     * @return
     */
    Optional<GameClient> getClient(GameType type);
}
