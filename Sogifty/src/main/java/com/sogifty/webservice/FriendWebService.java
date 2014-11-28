package com.sogifty.webservice;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sogifty.dao.dto.Gift;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.FriendService;
import com.sogifty.service.GiftService;
import com.sogifty.service.model.FriendModel;

@Path("/users/{userId}/friends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FriendWebService {

	private FriendService friendService = new FriendService();
	private GiftService giftService = new GiftService();

	@GET
	public Response getFriends(@PathParam("userId") int userId) {
		List<FriendModel> returnedFriends = null;

		try {
			returnedFriends = friendService.getFriends(userId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedFriends).build();
	}

	@POST
	public Response create(@PathParam("userId") int userId, FriendModel friend) {
		FriendModel returnedFriend = null;

		try {
			returnedFriend = friendService.create(userId, friend);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedFriend.getId()).build();
	}

	@Path("{friendId}")
	@PUT
	public Response update(@PathParam("userId") int userId, @PathParam("friendId") int friendId, FriendModel friend) {
		FriendModel returnedFriend = null;

		try {
			returnedFriend = friendService.update(userId, friendId, friend);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedFriend.getId()).build();
	}

	@Path("{friendId}")
	@DELETE
	public Response delete(@PathParam("friendId") int id) {
		try {
			friendService.delete(id);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@Path("{friendId}/gifts")
	@GET
	public Response getGifts(@PathParam("friendId") int friendId) {

		Set<Gift> returnedGifts = null;

		try {
			returnedGifts = giftService.getGifts(friendId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}

		return Response.ok(returnedGifts).build();
	}
}
