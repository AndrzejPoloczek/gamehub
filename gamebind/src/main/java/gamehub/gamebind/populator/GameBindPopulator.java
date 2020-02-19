package gamehub.gamebind.populator;

import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.Player;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GameBindPopulator implements Populator<GameBind, GameBindDTO> {

    @Override
    public void populate(GameBind source, GameBindDTO target) {
        target.setGuid(source.getGuid());
        target.setType(source.getType().name());
        target.setOwner(source.getOwner().getDisplayName());
        target.setPlayers(populatePlayers(source));
    }

    private List<String> populatePlayers(GameBind source) {
        return Optional.ofNullable(source)
                .map(GameBind::getPlayers)
                .orElse(Collections.emptyList())
                .stream()
                .map(Player::getDisplayName)
                .collect(Collectors.toList());
    }

    @Override
    public GameBindDTO populate(GameBind source) {
        GameBindDTO target = new GameBindDTO();
        populate(source, target);
        return target;
    }
}
