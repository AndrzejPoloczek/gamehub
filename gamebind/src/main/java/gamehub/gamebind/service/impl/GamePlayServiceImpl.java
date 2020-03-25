package gamehub.gamebind.service.impl;

import gamehub.gamebind.clients.GameClient;
import gamehub.gamebind.clients.OX3GameClient;
import gamehub.gamebind.service.GamePlayService;
import gamehub.sdk.enums.GameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GamePlayServiceImpl implements GamePlayService {

    private OX3GameClient ox3GameClient;

    @Override
    public Optional<GameClient> getClient(GameType type) {
        switch (type) {
            case OX3: return Optional.ofNullable(ox3GameClient);
            default: return Optional.empty();
        }
    }

    @Autowired
    public void setOx3GameClient(OX3GameClient ox3GameClient) {
        this.ox3GameClient = ox3GameClient;
    }
}
