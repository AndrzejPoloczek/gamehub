package gamehub.api.clients;

import gamehub.sdk.user.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import gamehub.api.config.FeignClientRequestConfiguration;

@FeignClient(name = "user-manager-service", configuration = FeignClientRequestConfiguration.class)
public interface UserManagerClient {

	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserInfoDTO> create(@RequestBody UserCreateDTO user);

	@GetMapping(path = "/get/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserInfoDTO> getByUsername(@PathVariable String username);

	@PostMapping(path = "/update/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserInfoDTO> update(@PathVariable String username, @RequestBody UserUpdateDTO user);

	@PostMapping(path = "/changePassword/{username}")
	ResponseEntity<Object> changePassword(@PathVariable String username, @RequestBody UserPasswordChangeDTO password);

	@PostMapping(path = "/authorize")
	ResponseEntity<Boolean> authorize(@RequestBody UserAuthorizeDTO auth);
}
