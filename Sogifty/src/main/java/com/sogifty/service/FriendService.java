package com.sogifty.service;

import com.sogifty.dao.FriendDAO;
import com.sogifty.dao.dto.Friend;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.FriendModel;

public class FriendService {
	private FriendDAO friendDAO = new FriendDAO();
	
	public FriendModel create(Friend friend) throws SogiftyException {
		return new FriendModel(friendDAO.create(friend));
	}
	
	public FriendModel update(Friend friend) throws SogiftyException {
		return new FriendModel(friendDAO.update(friend));
	}
	
	public void delete(Friend friend) throws SogiftyException {
		friendDAO.delete(friend);
	}
}
