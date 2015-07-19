package com.sogifty.dao;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.util.persistance.HibernateUtil;

/**
 * Data Access Object dealing with the Tag objects.
 **/
public class TagDAO extends AbstractDAO<Tag> {
	
	@Override
	public Class<Tag> getType() {
		return Tag.class;
	}

	/**
	 * Get the tags of a friend.
	 * @param friendId The id of the friend.
	 * @return A set of Tags.
	 * @throws SogiftyException
	 */
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
	
	/**
	 * Get a tag by its label. Used mostly to check if tag exists in database.
	 * @param label The label of the tag.
	 * @return The tag.
	 * @throws SogiftyException
	 */
	public Tag getByLabel(String label) throws SogiftyException {
		if (label == null) {
			logger.fatal("Could not find object without label in database");
			throw new SogiftyException(Response.Status.BAD_REQUEST);
		}
		label = label.toLowerCase();
		Session session = null;
		Tag found = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Tag.class);
			criteria.add(Restrictions.eq("label", label));
			found = (Tag) criteria.uniqueResult();
		} catch(HibernateException e) {
			logger.fatal("Error while reading object from database: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}

		return found;
	}
	
	/**
	 * Get all the existing tags labels.
	 * @return A set of labels.
	 * @throws SogiftyException
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getAllLabels() throws SogiftyException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(getType());
			return new HashSet<String>(criteria.setProjection(Projections.groupProperty("label")).list());
		} catch(HibernateException e) {
			logger.fatal("Error while reading Tags from database: " + e);
			throw new SogiftyException(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			closeSession(session);
		}
	}
}
