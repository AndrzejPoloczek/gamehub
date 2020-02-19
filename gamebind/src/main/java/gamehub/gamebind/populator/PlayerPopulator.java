package gamehub.gamebind.populator;

import gamehub.gamebind.model.Player;
import gamehub.sdk.dto.gamebind.PlayerDTO;
import org.springframework.stereotype.Component;

@Component
public class PlayerPopulator implements Populator<PlayerDTO, Player> {

    @Override
    public void populate(PlayerDTO source, Player target) {
        target.setUsername(source.getUsername());
        target.setDisplayName(source.getDisplayName());
    }

    @Override
    public Player populate(PlayerDTO source) {
        Player target = new Player();
        populate(source, target);
        return target;
    }
}
