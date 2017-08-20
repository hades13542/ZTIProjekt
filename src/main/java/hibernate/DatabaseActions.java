package hibernate;

import org.hibernate.Session;

import models.User;

public class DatabaseActions {

	public static boolean findUserInDatabase(String login, String password) {
        Session session = DatabaseConnection.getSession();
        session.beginTransaction();
        
        String query = "from User E where E.uname='"+login+"'";
        User user = (User) session.createQuery(query).uniqueResult();
        System.out.println("" + user.getUname() + user.getPassword() ); //TODO: remove
		return password.equals(user.getPassword());
	}

}
