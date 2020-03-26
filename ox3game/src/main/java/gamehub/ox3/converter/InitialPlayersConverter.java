package gamehub.ox3.converter;

import gamehub.sdk.dto.gamebind.PlayerDTO;
import gamehub.sdk.dto.gameplay.GameCreateDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InitialPlayersConverter implements Converter<GameCreateDTO, Map<String, String>> {

    @Override
    public Map<String, String> convert(GameCreateDTO source) {
        return source.getPlayers()
                .stream()
                .collect(Collectors.toMap(PlayerDTO::getUsername, PlayerDTO::getDisplayName));
    }
}
