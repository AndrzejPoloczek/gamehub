package gamehub.gamebind.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import gamehub.gamebind.dto.GameBindDTO;
import gamehub.gamebind.dto.GameCreateDTO;
import gamehub.gamebind.dto.GameJoinDTO;
import gamehub.gamebind.model.GameBindStatus;
import gamehub.gamebind.model.GameDefinition;
import gamehub.gamebind.model.GameType;

@RestController
public class GameBindController {

	@Autowired
	private AvailableGames games;
	
	@GetMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameDefinition>> getAvailableGames() {
		return ResponseEntity.ok().body(games.getGame());
	}
	
	@GetMapping(path = "/games/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameDefinition> getGame(@PathVariable(required = true) String type) {
		Optional<GameDefinition> game = games.getGameByType(GameType.valueOf(type));
		if (game.isPresent()) {
			return ResponseEntity.ok().body(game.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindDTO> create(@Valid @RequestBody(required = true) GameCreateDTO form) {
		return ResponseEntity.ok().body(mockGameBindDTO("mock_guid", form.getUsername(), Arrays.asList("owner")));
	}
	
	@PutMapping(path = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindDTO> join(@Valid @RequestBody(required = true) GameJoinDTO form) {
		return ResponseEntity.ok().body(mockGameBindDTO(form.getGuid(), "owner", Arrays.asList("owner", form.getUsername())));
	}
	
	@GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameBindDTO>> find() {
		return ResponseEntity.ok().body(Arrays.asList(
				mockGameBindDTO("guid_1", "owner_1", Arrays.asList("owner_1")),
				mockGameBindDTO("guid_2", "owner_2", Arrays.asList("owner_2", "other_player")),
				mockGameBindDTO("guid_3", "owner_3", Arrays.asList("owner_3"))));
	}
	
	@GetMapping(path = "/check/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBindStatus> check(@PathVariable(required = true) String guid) {
		return ResponseEntity.ok().body("ok".equals(guid) ? GameBindStatus.OPEN : GameBindStatus.CLOSED);
	}
	
	
	private GameBindDTO mockGameBindDTO(String guid, String owner, List<String> players) {
		GameBindDTO mock = new GameBindDTO();
		mock.setType(GameType.SAMPLE_GAME);
		mock.setGuid(guid);
		mock.setOwner(owner);
		mock.setPlayers(players);
		return mock;
	}
}
