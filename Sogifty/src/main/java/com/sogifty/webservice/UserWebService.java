package com.sogifty.webservice;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.UserService;
import com.sogifty.service.model.UserModel;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserWebService {
	private UserService userService = new UserService();
	
	@Path("register")
	@POST
	public Response register(User user) {
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
		UserModel returnedUser = null;

		try {
			returnedUser = userService.login(user);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedUser).build();
	}

	@Path("{userId}/friends")
	@GET
	public Response getFriends(@PathParam("userId") int userId) {
		Set<Friend> returnedFriends = null;

		try {
			returnedFriends = userService.getFriends(userId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedFriends).build();
	}
}
