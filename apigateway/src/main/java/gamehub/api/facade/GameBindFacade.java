package gamehub.api.facade;

import gamehub.api.dto.ApiCurrentGameDTO;
import gamehub.api.dto.ApiGameCreateDTO;
import gamehub.sdk.dto.gamebind.GameBindCheckDTO;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface GameBindFacade {

    /**
     * Create a new game
     * @param form
     * @return
     */
    ResponseEntity<GameBindDTO> create(ApiGameCreateDTO form);

    /**
     * Join to current game
     * @param guid
     * @return
     */
    ResponseEntity<GameBindDTO> join(String guid);

    /**
     * Find available games to join
     * @return
     */
    ResponseEntity<List<GameBindDTO>> find();

    /**
     * Find available games to join by type
     * @param type
     * @return
     */
    ResponseEntity<List<GameBindDTO>> findByType(String type);

    /**
     * Update okayer's status
     * @param guid
     * @return
     */
    ResponseEntity<GameBindCheckDTO> updatePlayerStatus(String guid);

    /**
     * Get current bind
     * @return
     */
    ResponseEntity<ApiCurrentGameDTO> getCurrentBind();

    /**
     * Cancel a game
     * @param guid
     * @return
     */
    ResponseEntity<Object> cancelBind(String guid);
}
