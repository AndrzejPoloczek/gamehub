package gamehub.gamebind.converter;

import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.Player;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GameBindConverter implements Converter<GameBind, GameBindDTO> {

    @Override
    public GameBindDTO convert(GameBind source) {
        GameBindDTO target = new GameBindDTO();
        target.setGuid(source.getGuid());
        target.setType(source.getType().name());
        target.setOwner(source.getOwner().getDisplayName());
        target.setPlayers(populatePlayers(source));
        return target;
    }

    private List<String> populatePlayers(GameBind source) {
        return Optional.ofNullable(source)
                .map(GameBind::getPlayers)
                .orElse(Collections.emptyList())
                .stream()
                .map(Player::getDisplayName)
                .collect(Collectors.toList());
    }
}
