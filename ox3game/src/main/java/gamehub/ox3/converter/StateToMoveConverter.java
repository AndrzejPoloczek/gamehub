package gamehub.ox3.converter;

import gamehub.ox3.dto.PlayDTO;
import gamehub.ox3.model.GameState;
import gamehub.ox3.validation.GamePlayValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StateToMoveConverter implements Converter<GameState, PlayDTO> {

    private GamePlayValidation gamePlayValidation;

    @Override
    public PlayDTO convert(GameState source) {
        final PlayDTO target = new PlayDTO();
        boolean isOver = gamePlayValidation.isGameOver(source);
        target.setArea(source.getArea());
        target.setOver(isOver);
        if (isOver) {
            target.setWinnerCoordinates(source.getWinnerCoordinates());
        }
        return target;
    }

    @Autowired
    public void setGamePlayValidation(GamePlayValidation gamePlayValidation) {
        this.gamePlayValidation = gamePlayValidation;
    }
}
