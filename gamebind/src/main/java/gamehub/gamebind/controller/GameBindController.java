package gamehub.gamebind.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import gamehub.gamebind.converter.CreateGameConverter;
import gamehub.gamebind.converter.GameBindConverter;
import gamehub.gamebind.converter.BindCheckConverter;
import gamehub.gamebind.converter.PlayerConverter;
import gamehub.gamebind.exception.GameBindException;
import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.service.GameBindService;
import gamehub.sdk.dto.gamebind.*;
import gamehub.sdk.enums.GameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gamehub.gamebind.config.AvailableGames;
import gamehub.gamebind.model.GameDefinition;

@RestController
public class GameBindController {

	@Resource
	private AvailableGames games;

	private GameBindService gameBindService;
	private GameBindConverter gameBindConverter;
	private CreateGameConverter createGameConverter;
	private PlayerConverter playerConverter;
	private BindCheckConverter bindCheckConverter;

	
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

	@PutMapping(path = "/update/player/status/{guid}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindCheckDTO> updatePlayerStatus(@PathVariable final String guid, @PathVariable final String username) throws GameBindException {
		final GameBind gameBind = gameBindService.updatePlayerStatus(guid, username);
		return ResponseEntity.ok().body(bindCheckConverter.convert(gameBind));
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

	@Autowired
	public void setBindCheckConverter(BindCheckConverter bindCheckConverter) {
		this.bindCheckConverter = bindCheckConverter;
	}
}
