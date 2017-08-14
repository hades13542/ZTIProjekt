package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConnection {

	public Session getSession() {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	return sessionFactory.openSession();	
	}
}
