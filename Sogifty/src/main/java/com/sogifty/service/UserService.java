package com.sogifty.service;

import java.util.Set;

import com.sogifty.dao.UserDAO;
import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.UserModel;

public class UserService {
	private UserDAO userDAO = new UserDAO();

	public UserModel register(User user) throws SogiftyException {
		return new UserModel(userDAO.create(user));
	}
	
	public UserModel login(User user) throws SogiftyException {
		return new UserModel(userDAO.getId(user));
	}

	public Set<Friend> getFriends(int userId) throws SogiftyException {
		return userDAO.getFriends(userId);
	}
}
