package jsf;

import hibernate.DatabaseActions;
import models.User;

public class LoginDAO {

	public static boolean validate(String login, String password) {
		if (!login.isEmpty() && !password.isEmpty()) {
			return DatabaseActions.findUserInDatabase(login, password);
		}
		return false;
	}

	public static boolean checkUsernameAvailability(String username) {
		if (!username.isEmpty()) {
			return DatabaseActions.isUsernameAvailable(username);
		}
		return false;
	}

	public static void createNewUser(String username, String password, String email) {
		User newUser = new User();
		newUser.setUname(username);
		newUser.setPassword(password);
		newUser.setEmail(email);
		DatabaseActions.createNewUser(newUser);
	}

}