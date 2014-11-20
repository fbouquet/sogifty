package com.sogifty.dao;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.sogifty.dao.dto.DTO;
import com.sogifty.exception.SogiftyException;
import com.sogifty.util.persistance.HibernateUtil;

public abstract class AbstractDAO<T extends DTO> {
	protected final Logger logger = Logger.getLogger(getClass());
	
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
	
	@SuppressWarnings("unchecked")
	public Integer update(T obj) throws SogiftyException {
		Session session = null;
		Transaction t = null;
		if(obj.getId() == null) {
			logger.fatal("Could not find object to update in database");
			throw new SogiftyException(Response.Status.NOT_FOUND);
		}
		T updatedObject = null;
		Integer updatedObjectId = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			updatedObject = (T) session.merge(obj);
			updatedObjectId = updatedObject.getId();
			t.commit();
		} catch(ConstraintViolationException e) {
			if(t != null) {
				t.rollback();
			}
			logger.fatal("This updated object violates some constraint in the db : "+ e);
			throw new SogiftyException(Response.Status.CONFLICT);
		}
		catch(Exception e) {
			if(t != null) {
				t.rollback();
			}
			logger.fatal("Could not update the object: "+ e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return updatedObjectId;
	}
	
	public void delete(T obj) throws SogiftyException {
		Session session = null;
		Transaction t = null;
		if(obj.getId() == null) {
			logger.fatal("Could not find object to delete in database");
			throw new SogiftyException(Response.Status.NOT_FOUND);
		}
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			session.delete(obj);
			t.commit();
		} catch(StaleStateException e) {
				if(t != null) {
					t.rollback();
				}
				logger.fatal("The object to delete doesn't exist: "+ e);
				throw new SogiftyException(Response.Status.NOT_FOUND);
		} catch(Exception e) {
			if(t != null) {
				t.rollback();
			}
			logger.fatal("Could not delete the object: "+ e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
