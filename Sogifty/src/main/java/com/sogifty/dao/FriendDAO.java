package com.sogifty.dao;

import java.util.Set;

import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.util.persistance.HibernateUtil;


public class FriendDAO extends AbstractDAO<Friend> {
	
	@Override
	public Class<Friend> getType() {
		return Friend.class;
	}
	
	public Set<Tag> getTags(int friendId) throws SogiftyException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Friend friend = getById(Integer.valueOf(friendId));
			return friend.getTags();
		} catch(HibernateException e) {
			logger.fatal("Error while reading friend from database: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}
	}
}
