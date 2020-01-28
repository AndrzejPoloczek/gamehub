package gamehub.usermanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gamehub.sdk.user.model.UserCreateDTO;
import gamehub.sdk.user.model.UserInfoDTO;
import gamehub.sdk.user.model.UserPasswordChangeDTO;
import gamehub.usermanager.dto.UserUpdateDTO;
import gamehub.usermanager.model.User;
import gamehub.usermanager.service.UserManagerService;

@RestController
public class UserManagerController {

	@Autowired
	private UserManagerService userManagerService;
	
	
	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> create(@Valid @RequestBody(required = true) UserCreateDTO user) {
		final UserInfoDTO info = populateUser(userManagerService.create(user.getDisplayName(), user.getUsername(), user.getPassword()));
		return ResponseEntity.ok().body(info);
	}
	
	@PostMapping(path = "/update/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> update(@PathVariable String username, @Valid @RequestBody(required = true) UserUpdateDTO user) {
		final UserInfoDTO info = populateUser(userManagerService.update(username, user.getDisplayName()));
		return ResponseEntity.ok().body(info);
	}
	
	@PostMapping(path = "/changePassword/{username}")
	public ResponseEntity<Object> changePassword(@PathVariable String username, @Valid @RequestBody(required = true) UserPasswordChangeDTO password) {
		userManagerService.changePassword(username, password.getPassword());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "/get/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> getByUsername(@PathVariable(required = true) String username) {
		final UserInfoDTO info = populateUser(userManagerService.get(username));
		return ResponseEntity.ok().body(info);
	}
	
	@GetMapping(path = "/authorize/{username}/{password}")
	public ResponseEntity<Boolean> authorize(@PathVariable(required = true) String username, @PathVariable(required = true) String password) {
		return ResponseEntity.ok().body(userManagerService.authorize(username, password));
	}
	
	private UserInfoDTO populateUser(User user) {
		final UserInfoDTO info = new UserInfoDTO();
		info.setDisplayName(user.getDisplayName());
		info.setUsername(user.getUsername());
		return info;
	}
}
