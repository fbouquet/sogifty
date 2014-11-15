package com.sogifty.dao;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.sogifty.exception.SogiftyException;
import com.sogifty.util.persistance.HibernateUtil;

public abstract class AbstractDAO<T> {
	private final Logger logger = Logger.getLogger(getClass());
	
	public Integer create(T obj) throws SogiftyException {
		Session session = null;
		Transaction t = null;
		Integer createdObjectId = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			createdObjectId = (Integer) session.save(obj);
			t.commit();
		} catch(ConstraintViolationException e) {
			if(t != null) {
				t.rollback();
			}
			logger.fatal("This object is already stored in the db : "+ e);
			throw new SogiftyException(Response.Status.CONFLICT);
		}
		catch(Exception e) {
			if(t != null) {
				t.rollback();
			}
			logger.fatal("Could not create the object: "+ e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return createdObjectId;
	}
}
