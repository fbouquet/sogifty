package com.sogifty.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sogifty.dao.dto.Gift;
import com.sogifty.exception.SogiftyException;

/**
 * Data Access Object dealing with the Gift objects.
 **/
public class GiftDAO extends AbstractDAO<Gift> {

	@Override
	public Class<Gift> getType() {
		return Gift.class;
	}

	/**
	 * Insert a list of gifts in the database.
	 * @param gifts The gifts to insert.
	 **/
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
		} catch(Exception e) {
			rollbackTransaction(t);
			throwAppropriateSogiftyExceptionForCreate(e);
		} finally {
			closeSession(session);
		}
	}

	/**
	 * Delete a list of gifts from the database.
	 * @param gift The gift to delete.
	 **/
	public void deleteGifts(List<Gift> gifts) throws SogiftyException {
		Session session = null;
		Transaction t = null;

		try {
			session = sessionFactory.openSession();
			t = session.beginTransaction();

			for (Gift gift : gifts) {
				session.delete(gift);
			}

			t.commit();
		} catch(Exception e) {
			rollbackTransaction(t);
			throwAppropriateSogiftyExceptionForDelete(e);
		} finally {
			closeSession(session);
		}
	}
}
