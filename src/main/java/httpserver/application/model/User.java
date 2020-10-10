package httpserver.application.model;

public class User {

	private final String name;
	private final String password;
	private final Profile profile = new Profile();

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Profile getProfile() {
		return profile;
	}
}
