package com.sogifty.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;

import com.sogifty.dao.FriendDAO;
import com.sogifty.dao.TagDAO;
import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.TagModel;

public class TagService {

	private TagDAO tagDAO = new TagDAO();
	private FriendDAO friendDAO = new FriendDAO();

	public Set<Tag> getTags(int friendId) throws SogiftyException {
		return tagDAO.getTags(friendId);
	}

	public TagModel create(TagModel tag, Integer userId, Integer friendId) throws SogiftyException {
		return new TagModel(tagDAO.create(new Tag().setLabel(tag.getLabel())));
	}

	public List<TagModel> findAll(Integer friendId) throws SogiftyException {
		List<TagModel> returnedTags = new LinkedList<TagModel>();
		Iterator<Tag> tagsIterator;
		if(friendId != null) {
			tagsIterator = (friendDAO.getTags(friendId.intValue())).iterator();
		} else {
			tagsIterator = tagDAO.findAll().iterator();
		}
		while (tagsIterator.hasNext()) {
			returnedTags.add(new TagModel(tagsIterator.next()));
		}
		return returnedTags;
	}

	public TagModel update(int tagId, TagModel tag, Integer userId, Integer friendId) throws SogiftyException {
		// TODO: check parameters correctly (right associated friend, right user associated to friend)
		checkParameters(tagId, tag, friendId);
		Friend friend = null;
		if(friendId != null) {
			friend = friendDAO.getById(friendId.intValue());
		}
		return new TagModel(tagDAO.update(new Tag().setId(tagId)
				.setLabel(tag.getLabel())));
	}

	public void delete(Integer tagId) throws SogiftyException {
		tagDAO.delete(tagId);
	}

	public void delete(Integer tagId, Integer userId, Integer friendId) throws SogiftyException {
		// TODO: check parameters (right associated friend, right user associated to friend)
		tagDAO.delete(tagId);
	}

	private void checkParameters(int tagId, TagModel tag, Integer friendId) throws SogiftyException {
		if (tag.getLabel() == null) {
			throw new SogiftyException(Response.Status.BAD_REQUEST);
		}
	}
}
