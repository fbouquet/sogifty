package com.sogifty.service;

import java.util.Set;

import com.sogifty.dao.TagDAO;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;

public class TagService {
	private TagDAO tagDAO = new TagDAO();
	
	public Set<Tag> getTags(int friendId) throws SogiftyException {
		return tagDAO.getTags(friendId);
	}
}
