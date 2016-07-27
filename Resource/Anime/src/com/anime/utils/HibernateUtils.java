package com.anime.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	public static final SessionFactory sessionFactory;

	static {
		try {
			Configuration configuration = new Configuration();
			configuration = configuration.configure();
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
			builder = builder.applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(builder.build());
			System.out.println(sessionFactory.toString());
		} catch (Throwable e) {
			System.err.println("SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

	public static Session currentSession() {
		Session s = session.get();

		if (s == null) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}

	public static void closeSession() {
		Session s = session.get();

		if (s != null) {
			s.close();
			session.set(null);
		}
	}
}
