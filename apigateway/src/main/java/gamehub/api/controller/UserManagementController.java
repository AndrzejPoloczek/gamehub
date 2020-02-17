package gamehub.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gamehub.api.clients.UserManagerClient;
import gamehub.sdk.user.model.UserCreateDTO;
import gamehub.sdk.user.model.UserInfoDTO;
import gamehub.sdk.user.model.UserPasswordChangeDTO;
import gamehub.sdk.user.model.UserUpdateDTO;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/user")
public class UserManagementController extends AbstractController {

	@Autowired
	private UserManagerClient userManagerClient;


	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> create(@Valid @RequestBody UserCreateDTO user) {
		user.setPassword(user.getPassword());
		return userManagerClient.create(user);
	}

	@GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> getByUsername() {
		validateSessionUser();
		return userManagerClient.getByUsername(getSessionUser().getUsername());

	}

	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> update(@Valid @RequestBody UserUpdateDTO user) {
		validateSessionUser();
		return userManagerClient.update(getSessionUser().getUsername(), user);
	}

	@PostMapping(path = "/changePassword")
	public ResponseEntity<Object> changePassword(@Valid @RequestBody UserPasswordChangeDTO password) {
		validateSessionUser();
		password.setPassword(password.getPassword());
		return userManagerClient.changePassword(getSessionUser().getUsername(), password);
	}
}
