package com.sogifty.service;

import com.sogifty.dao.UserDAO;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.UserException;
import com.sogifty.service.model.UserModel;

public class UserService {
	private UserDAO userDAO = new UserDAO();
	
	public UserModel register(User user) throws UserException {
		return new UserModel(userDAO.create(user));
	}
}
