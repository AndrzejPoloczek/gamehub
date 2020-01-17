package gamehub.usermanager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagerController {

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok().body("User Manager Service is UP!!");
	}
}
