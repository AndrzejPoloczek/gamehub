package gamehub.usermanager.service;

import gamehub.usermanager.model.User;

public interface UserManagerService {

	User create(String displayName, String username, String password);
	User update(String username, String displayName);
	User get(String username);
	void changePassword(String username, String newPassword);
	boolean authorize(String username, String password);
}
