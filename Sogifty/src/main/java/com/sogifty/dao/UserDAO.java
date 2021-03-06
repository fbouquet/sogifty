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
	
	@Override
	public Class<User> getType() {
		return User.class;
	}

	public Set<Friend> getFriends(int userId) throws SogiftyException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			User user = getById(Integer.valueOf(userId));
			return user.getFriends();
		} catch(HibernateException e) {
			logger.fatal("Error while reading user from database: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}
	}

	public Integer getId(User user) throws SogiftyException {
		Session session = null;
		Integer userFoundId = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", user.getEmail()));
			criteria.add(Restrictions.eq("pwd", user.getPwd()));
			User userFound = (User) criteria.uniqueResult();
			if (userFound != null) {
				userFoundId = userFound.getId();
			}
			else {
				throw new SogiftyException(Response.Status.NOT_FOUND);
			}
		} catch(HibernateException e) {
			logger.fatal("Error while reading user from database: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}
		return userFoundId;
	}
}
