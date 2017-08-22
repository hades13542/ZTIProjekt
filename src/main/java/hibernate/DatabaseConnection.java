package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConnection {

	static SessionFactory sessionFactory;

	public static Session getSession() {

		Configuration conf = new Configuration();
		conf.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
		conf.configure();
		sessionFactory = conf.buildSessionFactory();
		return sessionFactory.getCurrentSession();//openSession();
	}

	public static void closeSession() {
		sessionFactory.getCurrentSession().close();
	}
}
