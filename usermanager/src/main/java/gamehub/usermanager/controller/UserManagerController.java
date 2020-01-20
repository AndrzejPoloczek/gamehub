package gamehub.usermanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gamehub.usermanager.dto.UserCreateDTO;
import gamehub.usermanager.dto.UserInfoDTO;
import gamehub.usermanager.dto.UserPasswordChange;
import gamehub.usermanager.dto.UserUpdateDTO;
import gamehub.usermanager.validation.PasswordValidator;

@RestController
public class UserManagerController {

	@InitBinder("UserPasswordChange")
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(new PasswordValidator());
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok().body("User Manager Service is UP!!");
	}
	
	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> create(@Valid @RequestBody(required = true) UserCreateDTO user) {
		// Mock return data
		return ResponseEntity.ok().body(getMock("create"));
	}
	
	@PostMapping(path = "/update/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> update(@Valid @RequestBody(required = true) UserUpdateDTO user) {
		// Mock return data
		return ResponseEntity.ok().body(getMock("update"));
	}
	
	@PostMapping(path = "/changePassword/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> changePassword(@Valid @RequestBody(required = true) UserPasswordChange password) {
		// Mock return data
		return ResponseEntity.ok().body(getMock("changePassword"));
	}
	
	@GetMapping(path = "/get/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> getByUsername(@PathVariable(required = true) String username) {
		// Mock return data
		return ResponseEntity.ok().body(getMock("get"));
	}
	
	@GetMapping(path = "/authorize/{username}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> authorize(@PathVariable(required = true) String username) {
		// Mock return data
		return ResponseEntity.ok().body(getMock("authorize"));
	}
	
	
	private UserInfoDTO getMock(String action) {
		UserInfoDTO mock = new UserInfoDTO();
		mock.setDisplayName("Mocked User - " + action);
		mock.setUsername("mockeduser");
		return mock;
	}
}
