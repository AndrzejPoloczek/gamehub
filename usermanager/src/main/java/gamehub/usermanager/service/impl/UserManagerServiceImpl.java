package gamehub.usermanager.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import gamehub.usermanager.exception.UserAlreadyExistsException;
import gamehub.usermanager.exception.UserNotFoundException;
import gamehub.usermanager.model.User;
import gamehub.usermanager.service.UserManagerService;

@Service
public class UserManagerServiceImpl implements UserManagerService {

	private Set<User> users = new HashSet<>();
	
	@Override
	public User create(String displayName, String username, String password) {
		if (!getByUsername(username).isPresent()) {
			final User user = new User((StringUtils.isNotBlank(displayName) ? displayName : username), username, password);
			users.add(user);
			return user;
		} else {
			throw new UserAlreadyExistsException(String.format("User with username %s already exists", username));
		}
	}

	@Override
	public User update(String username, String displayName) {
		final User user = get(username);
		user.setDisplayName(displayName);
		return user;
	}

	@Override
	public User get(String username) {
		return getByUsername(username)
			.orElseThrow(() -> new UserNotFoundException(String.format("No user with username %s found", username)));
	}

	@Override
	public void changePassword(String username, String newPassword) {
		final User user = get(username);
		user.setPassword(newPassword);
	}

	@Override
	public boolean authorize(String username, String password) {
		return users.stream()
				.filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
				.findFirst()
				.isPresent();
	}
	
	private Optional<User> getByUsername(String username) {
		return users.stream()
				.filter(user -> user.getUsername().equals(username))
				.findFirst();
	}

}
