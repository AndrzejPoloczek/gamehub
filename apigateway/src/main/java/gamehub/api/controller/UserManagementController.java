package gamehub.api.controller;

import gamehub.api.facade.SessionUserFacade;
import gamehub.sdk.dto.user.UserCreateDTO;
import gamehub.sdk.dto.user.UserInfoDTO;
import gamehub.sdk.dto.user.UserPasswordChangeDTO;
import gamehub.sdk.dto.user.UserUpdateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "User management")
public class UserManagementController {

	private UserManagerClient userManagerClient;
	private SessionUserFacade sessionUserFacade;

	@ApiOperation(value = "Create a new user", response = UserInfoDTO.class)
	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> create(@Valid @RequestBody UserCreateDTO user) {
		return userManagerClient.create(user);
	}

	@ApiOperation(value = "Get user info", response = UserInfoDTO.class)
	@GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> getByUsername() {
		sessionUserFacade.validateSessionUser();
		return userManagerClient.getByUsername(sessionUserFacade.getSessionUser().getUsername());

	}

	@ApiOperation(value = "Update user", response = UserInfoDTO.class)
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> update(@Valid @RequestBody UserUpdateDTO user) {
		sessionUserFacade.validateSessionUser();
		return userManagerClient.update(sessionUserFacade.getSessionUser().getUsername(), user);
	}

	@ApiOperation(value = "Change user's password")
	@PostMapping(path = "/changePassword")
	public ResponseEntity<Object> changePassword(@Valid @RequestBody UserPasswordChangeDTO password) {
		sessionUserFacade.validateSessionUser();
		return userManagerClient.changePassword(sessionUserFacade.getSessionUser().getUsername(), password);
	}

	@Autowired
	public void setUserManagerClient(UserManagerClient userManagerClient) {
		this.userManagerClient = userManagerClient;
	}

	@Autowired
	public void setSessionUserFacade(SessionUserFacade sessionUserFacade) {
		this.sessionUserFacade = sessionUserFacade;
	}
}
