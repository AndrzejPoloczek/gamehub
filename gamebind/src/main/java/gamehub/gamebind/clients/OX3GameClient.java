package gamehub.gamebind.clients;

import gamehub.gamebind.config.FeignClientRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ox3-game-service", configuration = FeignClientRequestConfiguration.class)
public interface OX3GameClient extends GameClient {}
