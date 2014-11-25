package com.sogifty.service;

import javax.ws.rs.core.Response;

import com.sogifty.dao.UserDAO;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.UserModel;

public class UserService {
	
	private UserDAO userDAO = new UserDAO();

	public UserModel register(User user) throws SogiftyException {
		checkParameters(user);
		return new UserModel(userDAO.create(user));
	}
	
	public UserModel login(User user) throws SogiftyException {
		checkParameters(user);
		return new UserModel(userDAO.getId(user));
	}
	
	private void checkParameters(User user) throws SogiftyException {
		if (user.getEmail() == null || user.getPwd() == null) {
			throw new SogiftyException(Response.Status.BAD_REQUEST);
		}
	}
}
