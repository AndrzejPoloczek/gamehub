package gamehub.gameplay.converters;

import gamehub.gameplay.model.GamePlay;
import gamehub.gameplay.model.Player;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GameCreateToGamePlayConverter implements Converter<GameCreateDTO, GamePlay> {

    private PlayerConverter playerConverter;

    @Override
    public GamePlay convert(final GameCreateDTO source) {
        final GamePlay target = new GamePlay();
        target.setType(source.getGameType());
        target.setPlayers(convertPlayers(source));
        return target;
    }

    private List<Player> convertPlayers(final GameCreateDTO source) {
        return Optional.ofNullable(source.getPlayers())
                .orElse(Collections.emptyList())
                .stream()
                .map(playerConverter::convert)
                .collect(Collectors.toList());
    }

    @Autowired
    public void setPlayerConverter(PlayerConverter playerConverter) {
        this.playerConverter = playerConverter;
    }
}
