package gamehub.gamebind.converter;

import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.sdk.dto.gamebind.GameBindCheckDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BindCheckConverter implements Converter<GameBind, GameBindCheckDTO> {

    @Override
    public GameBindCheckDTO convert(GameBind source) {
        GameBindCheckDTO target = new GameBindCheckDTO();
        if (GameBindStatus.CLOSED.equals(source.getStatus()) && StringUtils.isNotBlank(source.getGamePlayGuid())) {
            target.setReady(true);
            target.setCanceled(false);
            target.setPlayGuid(source.getGamePlayGuid());
        } else {
            target.setReady(false);
            target.setCanceled(GameBindStatus.CANCELED.equals(source.getStatus()));
        }
        return target;
    }
}
