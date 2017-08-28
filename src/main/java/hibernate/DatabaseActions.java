package hibernate;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jsf.SessionUtils;
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

	public static void returnBook(Book book) {
		Session session = DatabaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Book tempBook = (Book) session.get(Book.class, book.getId());
			HttpSession httpSession = SessionUtils.getSession();
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
