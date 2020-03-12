package gamehub.gamebind.converter;

import gamehub.gamebind.model.GameDefinition;
import gamehub.sdk.dto.gamebind.GameDefinitionDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameDefinitionConverter implements Converter<GameDefinition, GameDefinitionDTO> {

    @Override
    public GameDefinitionDTO convert(GameDefinition source) {
        final GameDefinitionDTO target = new GameDefinitionDTO();
        target.setType(source.getType());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setMinPlayers(source.getMinPlayers());
        target.setMaxPlayers(source.getMaxPlayers());
        return target;
    }
}
