package gamehub.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gamehub.sdk.user.model.UserCreateDTO;
import gamehub.sdk.user.model.UserInfoDTO;


@RestController()
@RequestMapping(path = "/user")
public class UserManagementController {

	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> create(@Valid @RequestBody(required = true) UserCreateDTO user) {
		return ResponseEntity.ok().body(getMockUser());
	}
	
	@GetMapping(path = "/get/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> getByUsername(@PathVariable(required = true) String username) {
		return ResponseEntity.ok().body(getMockUser());
	}
	
	
	private UserInfoDTO getMockUser() {
		UserInfoDTO info = new UserInfoDTO();
		info.setUsername("username");
		info.setDisplayName("Mock User");
		return info;
	}
}
