package jsf;

import hibernate.DatabaseActions;

public class LoginDAO {

	public static boolean validate(String login, String password) {
		if(!login.isEmpty() && !password.isEmpty()) {
			return DatabaseActions.findUserInDatabase(login,password);
		}
		return false;
	}

	public static boolean checkUsernameAvailability(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void createNewUser(String username, String password, String email) {
		// TODO Auto-generated method stub
		
	}

}
