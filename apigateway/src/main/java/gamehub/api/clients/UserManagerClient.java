package gamehub.api.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import gamehub.api.config.FeignClientRequestConfiguration;
import gamehub.sdk.user.model.UserCreateDTO;
import gamehub.sdk.user.model.UserInfoDTO;
import gamehub.sdk.user.model.UserPasswordChangeDTO;
import gamehub.sdk.user.model.UserUpdateDTO;

@FeignClient(name = "user-manager-service", configuration = FeignClientRequestConfiguration.class)
public interface UserManagerClient {

	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> create(@RequestBody UserCreateDTO user);
	
	@GetMapping(path = "/get/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> getByUsername(@PathVariable String username);
	
	@PostMapping(path = "/update/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoDTO> update(@PathVariable String username, @RequestBody UserUpdateDTO user);
	
	@PostMapping(path = "/changePassword/{username}")
	public ResponseEntity<Object> changePassword(@PathVariable String username, @RequestBody UserPasswordChangeDTO password);
	
	@GetMapping(path = "/authorize/{username}/{password}")
	public ResponseEntity<Boolean> authorize(@PathVariable String username, @PathVariable String password);
}
