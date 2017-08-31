package hibernate;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jsf.SessionUtils;
import models.Book;
import models.User;

/**
 * Class to handle connection with datababase.
 */
public class DatabaseActions {

	final static Logger log = Logger.getLogger(DatabaseActions.class);

	private DatabaseActions() {
		//Access it with static way
	}
	
	/**
	 * Method to find user via login in database and if exist check if password is correct.
	 * @param login
	 * @param password
	 * @return true if given password match to password in database, otherwise false.
	 */
	public static boolean findUserInDatabase(String login, String password) {
		Session session = DatabaseConnection.getSession();
		session.beginTransaction();

		String query = "from User E where E.uname='" + login + "'";
		User user = (User) session.createQuery(query).uniqueResult();
		session.close();
		DatabaseConnection.closeSession();
		log.debug("" + user.getUname() + user.getPassword());
		return password.equals(user.getPassword());
	}

	/**
	 * Method to check if username is not already taken.
	 * @param username
	 * @return true if username is taken, otherwise false. 
	 */
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
		log.debug("" + user.getUname() + user.getPassword());
		return true;
	}

	/**
	 * Method to create new user in database
	 * @param newUser
	 */
	public static void createNewUser(User newUser) {
		Session session = DatabaseConnection.getSession();
		session.beginTransaction();
		session.save(newUser);
		session.getTransaction().commit();
		session.close();
		DatabaseConnection.closeSession();
	}

	/**
	 * Method to create new Book in database 
	 * @param book
	 */
	public static void addBook(Book book) {
		Session session = DatabaseConnection.getSession();
		session.beginTransaction();
		session.save(book);
		session.getTransaction().commit();
		session.close();
		DatabaseConnection.closeSession();
	}

	/**
	 * Method to update book instance that exist in database
	 * @param book
	 */
	public static void updateBook(Book book) {
		Session session = DatabaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Book tempBook = (Book) session.get(Book.class, book.getId());
			HttpSession httpSession = SessionUtils.getSession();
			tempBook.setRented((String) httpSession.getAttribute("username"));
			session.update(tempBook);
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * Method to fetch all books from database
	 * @return list of Books 
	 */
	public static Book[] getAllBooks() {
		Session session = DatabaseConnection.getSession();
        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
		List<Object> autorzy = session.createQuery("from Book").list();
 
        session.getTransaction().commit();
        for(Object b : autorzy) {
        	System.out.println((Book) b);
        }
        Book[] v = Arrays.copyOf(autorzy.toArray(), autorzy.toArray().length, Book[].class);
		return v;
	}

	/**
	 * Method to fetch all books rented by user from database
	 * @param id username
	 * @return list of rented books
	 */
	public static Book[] getYourBooks(String id) {
		Session session = DatabaseConnection.getSession();
        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
		List<Object> autorzy = session.createQuery("from Book where rented ='"+id+"'").list();
 
        session.getTransaction().commit();
        for(Object b : autorzy) {
        	System.out.println((Book) b);
        }
        Book[] v = Arrays.copyOf(autorzy.toArray(), autorzy.toArray().length, Book[].class);
		return v;
	}

	/**
	 * Method to mark book as not rented
	 * @param book
	 */
	public static void returnBook(Book book) {
		Session session = DatabaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Book tempBook = (Book) session.get(Book.class, book.getId());
			tempBook.setRented("no");
			session.update(tempBook);
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
 

}
