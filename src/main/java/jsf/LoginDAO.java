package jsf;

import hibernate.DatabaseActions;
import models.User;

/**
 * Data Access object to handle login into application.
 * Methods use direct connection with database.
 */
public class LoginDAO {

	/**
	 * Method to check if login and password are valid
	 * @param login
	 * @param password
	 * @return true if valid, otherwise false.
	 */
	public static boolean validate(String login, String password) {
		if (!login.isEmpty() && !password.isEmpty()) {
			return DatabaseActions.findUserInDatabase(login, password);
		}
		return false;
	}

	/**
	 * Method to check if username is available
	 * @param username
	 * @return false if username exist in database, otherwise true;
	 */
	public static boolean checkUsernameAvailability(String username) {
		if (!username.isEmpty()) {
			return DatabaseActions.isUsernameAvailable(username);
		}
		return false;
	}

	/**
	 * Create new user in database.
	 * @param username
	 * @param password
	 * @param email
	 */
	public static void createNewUser(String username, String password, String email) {
		User newUser = new User();
		newUser.setUname(username);
		newUser.setPassword(password);
		newUser.setEmail(email);
		DatabaseActions.createNewUser(newUser);
	}

}