package com.sogifty.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sogifty.dao.LoggingDAO;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.UserService;
import com.sogifty.service.model.UserModel;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserWebService {
	
	private UserService userService = new UserService();
	
	@Path("register")
	@POST
	public Response register(User user) {
		LoggingDAO.addLoggingData("UserWebService : registration of a new user [" + user.getEmail() + "].");
		UserModel returnedUser = null;

		try {
			returnedUser = userService.register(user);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedUser).build();
	}
	
	@Path("login")
	@POST
	public Response login(User user) {
		LoggingDAO.addLoggingData("UserWebService : login of the user [" + user.getEmail() + "].");
		UserModel returnedUser = null;

		try {
			returnedUser = userService.login(user);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedUser).build();
	}
}
