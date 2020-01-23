package gamehub.usermanager.model;

public class User {

	private String displayName;
	private String username;
	private String password;
	
	public User() {
		
	}
	
	public User(String displayName, String username, String password) {
		this.displayName = displayName;
		this.username = username;
		this.password = password;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
