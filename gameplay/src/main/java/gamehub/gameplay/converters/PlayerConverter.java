package gamehub.gameplay.converters;

import gamehub.gameplay.model.Player;
import gamehub.sdk.dto.gamebind.PlayerDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlayerConverter implements Converter<PlayerDTO, Player> {

    @Override
    public Player convert(PlayerDTO source) {
        Player target = new Player();
        target.setUsername(source.getUsername());
        target.setDisplayName(source.getDisplayName());
        return target;
    }
}
