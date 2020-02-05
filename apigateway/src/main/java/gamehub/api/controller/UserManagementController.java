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
import gamehub.sdk.user.model.UserPasswordChangeDTO;
import gamehub.sdk.user.model.UserUpdateDTO;


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
	
	@PostMapping(path = "/update/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> update(@PathVariable String username, @RequestBody(required = true) UserUpdateDTO user) {
		return userManagerClient.update(username, user);
	}
	
	@PostMapping(path = "/changePassword/{username}")
	public ResponseEntity<Object> changePassword(@PathVariable String username, @RequestBody(required = true) UserPasswordChangeDTO password) {
		return userManagerClient.changePassword(username, password);
	}
	
	@GetMapping(path = "/authorize/{username}/{password}")
	public ResponseEntity<Boolean> authorize(@PathVariable(required = true) String username, @PathVariable(required = true) String password) {
		return userManagerClient.authorize(username, password);
	}
}
