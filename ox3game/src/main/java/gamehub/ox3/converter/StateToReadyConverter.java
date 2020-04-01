package gamehub.ox3.converter;

import gamehub.ox3.dto.ReadyDTO;
import gamehub.ox3.model.GameState;
import gamehub.ox3.model.OX3State;
import gamehub.ox3.service.GameStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StateToReadyConverter implements Converter<GameState, ReadyDTO> {

    private GameStateService gameStateService;

    @Override
    public ReadyDTO convert(GameState source) {
        final ReadyDTO target = new ReadyDTO();
        target.setReady(gameStateService.isReady(source));
        if (target.isReady()) {
            target.setArea(source.getArea());
        }
        return target;
    }

    @Autowired
    public void setGameStateService(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }
}
