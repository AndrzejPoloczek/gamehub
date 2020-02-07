package gamehub.gamebind.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import gamehub.gamebind.model.GameDefinition;
import gamehub.gamebind.model.GameType;

@Component
@PropertySource("classpath:available-games.properties")
@ConfigurationProperties(prefix = "games")
public class AvailableGames {

	private List<GameDefinition> game;

	public List<GameDefinition> getGame() {
		return game;
	}

	public void setGame(List<GameDefinition> game) {
		this.game = game;
	}
	
	
	public Optional<GameDefinition> getGameByType(GameType type) {
		return getGame().stream()
				.filter(current -> current.getType().equals(type))
				.findFirst();
	}
}
