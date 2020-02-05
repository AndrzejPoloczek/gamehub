package gamehub.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gamehub.api.clients.UserManagerClient;
import gamehub.sdk.user.model.UserCreateDTO;
import gamehub.sdk.user.model.UserInfoDTO;


@RestController
@RequestMapping(path = "/user")
public class UserManagementController {

	@Autowired
	private UserManagerClient userManagerClient;
	
	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> create(@RequestBody(required = true) UserCreateDTO user) {
		return userManagerClient.create(user);
	}
	
	@GetMapping(path = "/get/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> getByUsername(@PathVariable(required = true) String username) {
		return userManagerClient.getByUsername(username);
	}
}
