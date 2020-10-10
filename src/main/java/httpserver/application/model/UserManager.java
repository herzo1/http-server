package httpserver.application.model;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

	private static final Map<String, User> users = new HashMap<>();

	public static void register(User user) throws Exception {
		if (users.containsKey(user.getName())) {
			throw new Exception("User already exists");
		}
		users.put(user.getName(), user);
	}

	public static User authenticate(String username, String password) throws Exception {
		User user = users.get(username);
		if (user == null || !user.getPassword().equals(password)) {
			throw new Exception("Invalid username or password");
		}
		return user;
	}
}
