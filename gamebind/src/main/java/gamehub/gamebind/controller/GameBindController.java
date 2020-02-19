package gamehub.gamebind.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.populator.GameBindPopulator;
import gamehub.gamebind.populator.PlayerPopulator;
import gamehub.gamebind.service.GameBindService;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import gamehub.sdk.dto.gamebind.GameCreateDTO;
import gamehub.sdk.dto.gamebind.GameJoinDTO;
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
import gamehub.gamebind.model.GameType;

@RestController
public class GameBindController {

	@Resource
	private AvailableGames games;

	private GameBindService gameBindService;
	private PlayerPopulator playerPopulator;
	private GameBindPopulator gameBindPopulator;

	
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
		final GameBindDTO game = gameBindPopulator.populate(gameBindService.create(GameType.valueOf(form.getType()), playerPopulator.populate(form), form.getPlayers()));
		return ResponseEntity.ok().body(game);
	}

	// TODO MOCKED
	@PutMapping(path = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindDTO> join(@Valid @RequestBody GameJoinDTO form) {
		return ResponseEntity.ok().body(mockGameBindDTO(form.getGuid(), "owner", Arrays.asList("owner", form.getUsername())));
	}

	@GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameBindDTO>> find() {
		return ResponseEntity.ok().body(gameBindService.getAvailableGames()
				.stream()
				.map(gameBindPopulator::populate)
				.collect(Collectors.toList()));
	}

	// TODO MOCKED
	@GetMapping(path = "/check/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindStatus> check(@PathVariable(required = true) String guid) {
		return ResponseEntity.ok().body("ok".equals(guid) ? GameBindStatus.OPEN : GameBindStatus.CLOSED);
	}
	
	
	private GameBindDTO mockGameBindDTO(String guid, String owner, List<String> players) {
		GameBindDTO mock = new GameBindDTO();
		mock.setType("SAMPLE_GAME");
		mock.setGuid(guid);
		mock.setOwner(owner);
		mock.setPlayers(players);
		return mock;
	}

	@Autowired
	public void setGameBindService(GameBindService gameBindService) {
		this.gameBindService = gameBindService;
	}

	@Autowired
	public void setPlayerPopulator(PlayerPopulator playerPopulator) {
		this.playerPopulator = playerPopulator;
	}

	@Autowired
	public void setGameBindPopulator(GameBindPopulator gameBindPopulator) {
		this.gameBindPopulator = gameBindPopulator;
	}
}
