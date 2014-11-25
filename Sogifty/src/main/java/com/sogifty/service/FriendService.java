package com.sogifty.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.sogifty.dao.FriendDAO;
import com.sogifty.dao.UserDAO;
import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.FriendModel;

public class FriendService {
	
	private FriendDAO friendDAO = new FriendDAO();
	private UserDAO userDAO = new UserDAO();

	public FriendModel create(int userId, FriendModel friend) throws SogiftyException {
		User user = userDAO.getById(userId);
		return new FriendModel(friendDAO.create(new Friend().setName(friend.getName())
															.setBirthdate(friend.getBirthdate())
															.setUser(user)));
	}

	public FriendModel update(int userId, int friendId, FriendModel friend) throws SogiftyException {
		checkParameters(userId, friendId, friend);
		User user = userDAO.getById(userId);
		return new FriendModel(friendDAO.update(new Friend().setId(friendId)
															.setName(friend.getName())
															.setBirthdate(friend.getBirthdate())
															.setUser(user)));
	}
	
//	
//	public FriendModel update(Friend friend) throws SogiftyException {
//		return new FriendModel(friendDAO.update(friend));
//	}

	public void delete(Friend friend) throws SogiftyException {
		friendDAO.delete(friend);
	}

	public List<FriendModel> getFriends(int userId) throws SogiftyException {
		List<FriendModel> returnedFriends = new LinkedList<FriendModel>();
		Iterator<Friend> friendsIterator = userDAO.getFriends(userId).iterator();
		while (friendsIterator.hasNext()) {
			returnedFriends.add(new FriendModel(friendsIterator.next()));
		}
		return returnedFriends;
	}
	
	private void checkParameters(int userId, int friendId, FriendModel friend) throws SogiftyException {
//		if (friendDAO.getById(friendId).getUser().getId().intValue() != userId) {
//			throw new SogiftyException(Response.Status.FORBIDDEN);
//		}
		if (friend.getName() == null || friend.getBirthdate() == null) {
			throw new SogiftyException(Response.Status.BAD_REQUEST);
		}
	}
}
