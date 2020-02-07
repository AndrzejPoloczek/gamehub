package gamehub.gamebind.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gamehub.gamebind.config.AvailableGames;
import gamehub.gamebind.dto.GameCreateDTO;
import gamehub.gamebind.model.GameDefinition;

@Component
public class GameCreationValidator implements ConstraintValidator<GameCreation, GameCreateDTO> {

	@Autowired
	private AvailableGames games;
	
	@Override
	public boolean isValid(GameCreateDTO value, ConstraintValidatorContext context) {
		
		final Optional<GameDefinition> gameDefOpt = games.getGameByType(value.getType());
		if (!gameDefOpt.isPresent()) {
			raiseError(context, "type", String.format("No available game with type %s found", value.getType()));
			return false;
		}
		
		final GameDefinition gameDef = gameDefOpt.get();
		if (value.getPlayers() < gameDef.getMinPlayers() || value.getPlayers() > gameDef.getMaxPlayers()) {
			raiseError(context, "players", String.format("Players count must be between %s and %s", gameDef.getMinPlayers(), gameDef.getMaxPlayers()));
			return false;
		}
		
		return true;
	}

	private void raiseError(ConstraintValidatorContext context, String field,  String message) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode(field)
				.addConstraintViolation();
	}
	
}
