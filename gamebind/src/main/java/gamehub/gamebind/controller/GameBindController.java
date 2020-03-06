package gamehub.gamebind.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import gamehub.gamebind.converter.CreateGameConverter;
import gamehub.gamebind.converter.GameBindConverter;
import gamehub.gamebind.converter.PlayerConverter;
import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.service.GameBindService;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import gamehub.sdk.dto.gamebind.GameCreateDTO;
import gamehub.sdk.dto.gamebind.GameJoinDTO;
import gamehub.sdk.enums.GameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gamehub.gamebind.config.AvailableGames;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.gamebind.model.GameDefinition;

@RestController
public class GameBindController {

	@Resource
	private AvailableGames games;

	private GameBindService gameBindService;
	private GameBindConverter gameBindConverter;
	private CreateGameConverter createGameConverter;
	private PlayerConverter playerConverter;

	
	@GetMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameDefinition>> getAvailableGames() {
		return ResponseEntity.ok().body(games.getGame());
	}
	
	@GetMapping(path = "/games/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameDefinition> getGame(@PathVariable String type) {
		Optional<GameDefinition> game = games.getGameByType(GameType.valueOf(type));
		if (game.isPresent()) {
			return ResponseEntity.ok().body(game.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindDTO> create(@Valid @RequestBody GameCreateDTO form) throws GameBindException {
		final GameBindDTO game = gameBindConverter.convert(gameBindService.create(createGameConverter.convert(form)));
		return ResponseEntity.ok().body(game);
	}

	@PutMapping(path = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindDTO> join(@Valid @RequestBody GameJoinDTO form) throws GameBindException {
		final GameBindDTO game = gameBindConverter.convert(gameBindService.join(form.getGuid(), playerConverter.convert(form)));
		return ResponseEntity.ok().body(game);
	}

	@GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameBindDTO>> find() {
		return ResponseEntity.ok().body(gameBindService.getAvailableGames()
				.stream()
				.map(gameBindConverter::convert)
				.collect(Collectors.toList()));
	}

	@GetMapping(path = "/find/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameBindDTO>> findByType(@PathVariable String type) {
		return ResponseEntity.ok().body(gameBindService.getAvailableGames(GameType.valueOf(type))
				.stream()
				.map(gameBindConverter::convert)
				.collect(Collectors.toList()));
	}

	// TODO MOCKED
	@GetMapping(path = "/check/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindStatus> check(@PathVariable(required = true) String guid) {
		return ResponseEntity.ok().body("ok".equals(guid) ? GameBindStatus.OPEN : GameBindStatus.CLOSED);
	}


	@Autowired
	public void setGameBindService(GameBindService gameBindService) {
		this.gameBindService = gameBindService;
	}

	@Autowired
	public void setGameBindConverter(GameBindConverter gameBindConverter) {
		this.gameBindConverter = gameBindConverter;
	}

	@Autowired
	public void setCreateGameConverter(CreateGameConverter createGameConverter) {
		this.createGameConverter = createGameConverter;
	}

	@Autowired
	public void setPlayerConverter(PlayerConverter playerConverter) {
		this.playerConverter = playerConverter;
	}
}
