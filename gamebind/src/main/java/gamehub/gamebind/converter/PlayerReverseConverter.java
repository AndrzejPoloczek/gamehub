package gamehub.gamebind.converter;

import gamehub.gamebind.model.Player;
import gamehub.sdk.dto.gamebind.PlayerDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlayerReverseConverter implements Converter<Player, PlayerDTO> {

    @Override
    public PlayerDTO convert(Player source) {
        PlayerDTO target = new PlayerDTO();
        target.setUsername(source.getUsername());
        target.setDisplayName(source.getDisplayName());
        return target;
    }
}
