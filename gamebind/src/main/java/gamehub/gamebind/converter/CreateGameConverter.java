package gamehub.gamebind.converter;

import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.gamebind.model.Player;
import gamehub.sdk.dto.gamebind.GameCreateDTO;
import gamehub.sdk.enums.GameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateGameConverter implements Converter<GameCreateDTO, GameBind> {

    private PlayerConverter playerConverter;

    @Override
    public GameBind convert(GameCreateDTO source) {
        final GameBind target = new GameBind();
        final Player creator = playerConverter.convert(source);
        final List<Player> playersList = new ArrayList<>();
        playersList.add(creator);

        target.setType(GameType.valueOf(source.getType()));
        target.setOwner(creator);
        target.setExpectedPlayers(source.getPlayers());
        target.setStatus(GameBindStatus.OPEN);
        target.setPlayers(playersList);

        return target;
    }

    @Autowired
    public void setPlayerConverter(PlayerConverter playerConverter) {
        this.playerConverter = playerConverter;
    }
}
