package gamehub.usermanager.service;

import gamehub.usermanager.model.User;

public interface UserManagerService {

	/**
	 * Create a new user
	 * @param displayName User's display name
	 * @param username User's username
	 * @param password User's password
	 * @return
	 */
	User create(String displayName, String username, String password);
	
	/**
	 * Update existing user
	 * @param username User's username
	 * @param displayName New user's display name
	 * @return
	 */
	User update(String username, String displayName);
	
	/**
	 * Get user by username
	 * @param username Users's username
	 * @return
	 */
	User get(String username);
	
	/**
	 * Change password for user
	 * @param username User's username
	 * @param newPassword New user's password
	 */
	void changePassword(String username, String newPassword);
	
	/**
	 * Authorize user by username and password.
	 * @param username User's username
	 * @param password User's password
	 * @return
	 */
	boolean authorize(String username, String password);
}
