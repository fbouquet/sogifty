package com.sogifty.dao;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.sogifty.dao.dto.DTO;
import com.sogifty.exception.SogiftyException;
import com.sogifty.util.persistance.HibernateUtil;

public abstract class AbstractDAO<T extends DTO> {

	protected final Logger logger = Logger.getLogger(getClass());
	protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public final Integer create(T obj) throws SogiftyException {
		Session session = null;
		Transaction t = null;
		Integer createdObjectId = null;
		try {
			session = sessionFactory.openSession();
			t = session.beginTransaction();
			createdObjectId = (Integer) session.save(obj);
			t.commit();
		} catch(ConstraintViolationException e) {
			rollbackTransaction(t);
			logger.fatal("This object is already stored in the db : " + e);
			throw new SogiftyException(Response.Status.CONFLICT);
		}
		catch(Exception e) {
			rollbackTransaction(t);
			logger.fatal("Could not create the object: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}

		return createdObjectId;
	}

	public final Integer update(T obj) throws SogiftyException {
		if (obj.getId() == null) {
			logger.fatal("Could not find object to update in database");
			throw new SogiftyException(Response.Status.NOT_FOUND);
		}
		Session session = null;
		Transaction t = null;
		T updatedObject = null;
		Integer updatedObjectId = null;
		try {
			session = sessionFactory.openSession();
			Integer id = obj.getId();
			t = session.beginTransaction();
			updatedObject = getById(id);
			if (updatedObject == null) {
				logger.fatal("retrieved object = null in update!");
				throw new SogiftyException(Response.Status.NOT_FOUND);
			}
			updatedObject = updateFields(updatedObject, obj);
			session.update(updatedObject);
			updatedObjectId = updatedObject.getId();
			t.commit();
		} catch(ConstraintViolationException e) {
			rollbackTransaction(t);
			logger.fatal("This updated object violates some constraint in the db : " + e + e.getMessage());
			throw new SogiftyException(Response.Status.CONFLICT);
		} catch(Exception e) {
			rollbackTransaction(t);
			logger.fatal("Could not update the object: " + e + e.getMessage());
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}

		return updatedObjectId;
	}

	@SuppressWarnings("unchecked")
	private final T updateFields(T objectToUpdate, T updatedObject) throws SogiftyException {
		T updated = null;
		try {
			T instance = getType().newInstance();
			updated = (T) instance.updateFields(objectToUpdate, updatedObject);
		} catch (InstantiationException e) {
			logger.fatal("Could not update the object: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} catch (IllegalAccessException e) {
			logger.fatal("Could not update the object: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return updated;
	}

	public final void delete(Integer id) throws SogiftyException {
		Session session = null;
		Transaction t = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			T objectToDelete = getById(id);
			session.delete(objectToDelete);
			t.commit();
		} catch(StaleStateException e) {
			rollbackTransaction(t);
			logger.fatal("The object to delete doesn't exist: " + e);
			throw new SogiftyException(Response.Status.NOT_FOUND);
		} catch(Exception e) {
			rollbackTransaction(t);
			logger.fatal("Could not delete the object: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final Set<T> findAll() throws SogiftyException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(getType());
			return new HashSet<T>(criteria.list());
		} catch(HibernateException e) {
			logger.fatal("Error while reading from database: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final T getById(Integer id) throws SogiftyException {
		if (id == null) {
			logger.fatal("Could not find object without id in database");
			throw new SogiftyException(Response.Status.BAD_REQUEST);
		}
		Session session = null;
		T found = null;
		try {
			session = sessionFactory.openSession();
			found = (T) session.get(getType(), id);
			if (found == null) {
				throw new SogiftyException(Response.Status.NOT_FOUND);
			}
		} catch(HibernateException e) {
			logger.fatal("Error while reading object from database: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}

		return found;
	}
	
	/***
	 * Get the class that the DAO accesses.
	 * @return the class that the DAO accesses. e.g.: in UserDAO -> return User.class
	 */
	// Function used in template methods: create, update, updateFields, delete, findAll, getById
	public abstract Class<T> getType();
	
	protected final void closeSession(Session session) {
		if (session != null) {
			session.close();
		}
	}

	protected final void rollbackTransaction(Transaction t) {
		if(t != null) {
			t.rollback();
		}
	}
}
