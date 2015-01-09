package com.sogifty.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.sogifty.dao.TagDAO;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.TagModel;

public class TagService {

	private TagDAO tagDAO = new TagDAO();

	public Set<Tag> getTags(int friendId) throws SogiftyException {
		return tagDAO.getTags(friendId);
	}

	public List<TagModel> findAll() throws SogiftyException {
		List<TagModel> tags = new LinkedList<TagModel>();
		Iterator<String> tagsIterator;

		tagsIterator = tagDAO.getAllLabels().iterator();

		while (tagsIterator.hasNext()) {
			tags.add(new TagModel(tagsIterator.next()));
		}
		return tags;
	}

	public void delete(Integer tagId) throws SogiftyException {
		tagDAO.delete(tagId);
	}

	public void delete(Integer tagId, Integer userId, Integer friendId) throws SogiftyException {
		tagDAO.delete(tagId);
	}
}
