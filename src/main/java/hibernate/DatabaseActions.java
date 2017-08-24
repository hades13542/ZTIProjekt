package hibernate;

import org.hibernate.Session;

import models.Book;
import models.User;

public class DatabaseActions {

	public static boolean findUserInDatabase(String login, String password) {
		Session session = DatabaseConnection.getSession();
		session.beginTransaction();

		String query = "from User E where E.uname='" + login + "'";
		User user = (User) session.createQuery(query).uniqueResult();
		session.close();
		DatabaseConnection.closeSession();
		System.out.println("" + user.getUname() + user.getPassword()); // TODO: remove
		return password.equals(user.getPassword());
	}

	public static boolean isUsernameAvailable(String username) {
		Session session = DatabaseConnection.getSession();
		session.beginTransaction();

		String query = "from User E where E.uname='" + username + "'";
		User user = (User) session.createQuery(query).uniqueResult();
		
		session.close();
		DatabaseConnection.closeSession();
		if (user == null) {
			return false;
		}
		System.out.println("" + user.getUname() + user.getPassword()); // TODO: remove
		return true;
	}

	public static void createNewUser(User newUser) {
		Session session = DatabaseConnection.getSession();
		session.beginTransaction();
		session.save(newUser);
		session.getTransaction().commit();
		session.close();
		DatabaseConnection.closeSession();
	}

	public static void addBook(Book book) {
		Session session = DatabaseConnection.getSession();
		session.beginTransaction();
		session.save(book);
		session.getTransaction().commit();
		session.close();
		DatabaseConnection.closeSession();
	}

}
