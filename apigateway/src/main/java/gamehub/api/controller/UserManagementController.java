package gamehub.api.controller;

import gamehub.sdk.dto.user.UserCreateDTO;
import gamehub.sdk.dto.user.UserInfoDTO;
import gamehub.sdk.dto.user.UserPasswordChangeDTO;
import gamehub.sdk.dto.user.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gamehub.api.clients.UserManagerClient;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/user")
public class UserManagementController extends AbstractController {

	private UserManagerClient userManagerClient;


	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> create(@Valid @RequestBody UserCreateDTO user) {
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
		return userManagerClient.changePassword(getSessionUser().getUsername(), password);
	}

	@Autowired
	public void setUserManagerClient(UserManagerClient userManagerClient) {
		this.userManagerClient = userManagerClient;
	}
}
