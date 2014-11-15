package com.sogifty.service;

import com.sogifty.dao.UserDAO;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.UserModel;

public class UserService {
	private UserDAO userDAO = new UserDAO();
	
	public UserModel register(User user) throws SogiftyException {
		return new UserModel(userDAO.create(user));
	}
}
