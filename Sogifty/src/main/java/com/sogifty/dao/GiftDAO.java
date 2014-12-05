package com.sogifty.dao;

import java.util.List;

import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.sogifty.dao.dto.Gift;
import com.sogifty.exception.SogiftyException;

public class GiftDAO extends AbstractDAO<Gift> {

	public void createGifts(List<Gift> gifts) throws SogiftyException {
		Session session = null;
		Transaction t = null;

		try {
			session = sessionFactory.openSession();
			t = session.beginTransaction();
			
			for (Gift gift : gifts) {
				session.save(gift);
			}

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
	}
}
