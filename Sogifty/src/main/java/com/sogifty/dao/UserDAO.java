package com.sogifty.dao;

import java.util.Set;

import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.SogiftyException;
import com.sogifty.util.persistance.HibernateUtil;


public class UserDAO extends AbstractDAO<User> {
	public Set<Friend> getFriends(int userId) throws SogiftyException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", userId));
			User user = (User) criteria.uniqueResult();
			return user.getFriends();
		} catch(HibernateException e) {
			logger.fatal("Error while reading user from database: "+ e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
