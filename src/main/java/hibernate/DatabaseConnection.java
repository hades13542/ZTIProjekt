package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Basic class handling database Connection using Hibernate
 */
public class DatabaseConnection {

	static SessionFactory sessionFactory = null;

	/**
	 * Method to get database session and configure hibernate connection
	 * @return current session or create a new one
	 */
	public static Session getSession() {
		if (sessionFactory != null) {
			return sessionFactory.getCurrentSession();
		}
		Configuration conf = new Configuration();
		conf.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
		conf.configure();
		sessionFactory = conf.buildSessionFactory();
		return sessionFactory.getCurrentSession();// openSession();
	}

	/**
	 * Close database session
	 */
	public static void closeSession() {
		sessionFactory.getCurrentSession().close();
	}
}
