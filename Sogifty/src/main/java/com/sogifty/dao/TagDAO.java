package com.sogifty.dao;

import java.util.Set;

import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.util.persistance.HibernateUtil;

public class TagDAO extends AbstractDAO<Tag> {
	public Set<Tag> getTags(int friendId) throws SogiftyException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Friend.class);
			criteria.add(Restrictions.eq("id", friendId));
			
			Friend friend = (Friend) criteria.uniqueResult();
			if (friend == null) {
				throw new SogiftyException(Response.Status.NOT_FOUND);
			}
			
			return friend.getTags();
		} catch(HibernateException e) {
			logger.fatal("Error while reading friend from database: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}
	}
}
