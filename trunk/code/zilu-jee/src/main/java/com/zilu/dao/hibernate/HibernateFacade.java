package com.zilu.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;


public class HibernateFacade {
	
	private static final Logger logger = Logger.getLogger(HibernateFacade.class);
	
    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> threadT = new ThreadLocal<Transaction>();
	private static AnnotationConfiguration configuration = new AnnotationConfiguration();
    private static org.hibernate.SessionFactory sessionFactory;
    private static List<Class> entityClasses = new ArrayList<Class>();
    
    private HibernateFacade() {
    }
	
	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    
    public static void addEntityClass(Class clazz) {
    	entityClasses.add(clazz);
    }
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()|| !session.isConnected()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

        return session;
    }
    
    public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null) {
			rebuildSessionFactory();
		}
    	return sessionFactory;
    }

	/**
     *  Rebuild hibernate session factory
     *
     */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(CONFIG_FILE_LOCATION);
			for (Class clazz : entityClasses) {
				logger.info("add Entity " + clazz);
				configuration.addAnnotatedClass(clazz);
			}
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("%%%% Error Creating SessionFactory %%%%");
		}
	}

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        try {
	    	Session session = (Session) threadLocal.get();
	       
	        if (session != null) {
	            session.close();
	        }
	        threadLocal.set(null);
	        threadT.set(null);
        } catch (Exception e) {
        	
        }
    }
    
    public static void beginTransaction() {
    	Transaction transaction = threadT.get();
    	if (transaction != null&& transaction.isActive()) {
    		return;
    	}
    	else {
    		transaction = getSession().beginTransaction();
	    	threadT.set(transaction);
    	}
    }
    
    public static void commitTransaction() {
    	Transaction transaction = threadT.get();
    	if (transaction == null|| transaction.wasCommitted()|| transaction.wasRolledBack()) {
    		throw new HibernateException("transaction never begin, or not active");
    	}
    	transaction.commit();
    	threadT.set(null);
    }
    
    public static void rollbackTransaction() {
    	Transaction transaction = threadT.get();
//    	if (transaction == null) {
//    		throw new HibernateException("transaction never begin, or not active");
//    	}
    	try {
    		transaction.rollback();
    	} catch (Exception e) {
    	}
    	threadT.set(null);
    }
    
    public static void main(String[] args) {
    	rebuildSessionFactory();
    }
}
