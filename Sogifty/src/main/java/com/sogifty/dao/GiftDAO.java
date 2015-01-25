package com.sogifty.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sogifty.dao.dto.Gift;
import com.sogifty.exception.SogiftyException;

public class GiftDAO extends AbstractDAO<Gift> {
	
	@Override
	public Class<Gift> getType() {
		return Gift.class;
	}

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
		}finally {
			closeSession(session);
		}
	}
	
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
