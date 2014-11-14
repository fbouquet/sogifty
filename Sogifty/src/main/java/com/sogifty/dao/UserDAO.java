package com.sogifty.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import javax.ws.rs.core.Response;

import com.sogifty.dao.dto.User;
import com.sogifty.exception.UserException;
import com.sogifty.util.persistance.HibernateUtil;


public class UserDAO {
	private static final Logger logger = Logger.getLogger(UserDAO.class);
	
	public Integer create(User user) throws UserException {
		Session session = null;
		Transaction t;
		Integer createdUserId = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			createdUserId = (Integer) session.save(user);
			t.commit();
		} catch(ConstraintViolationException e) {
			logger.fatal("This user is already stored in the db : "+ e);
			throw new UserException(Response.Status.CONFLICT);
		}
		catch(Exception e) {
			logger.fatal("Could not create user : "+ e);
			throw new UserException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return createdUserId;
	}
}
