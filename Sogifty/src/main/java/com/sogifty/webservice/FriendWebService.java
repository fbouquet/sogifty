package com.sogifty.webservice;

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

import org.apache.log4j.Logger;

import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.Gift;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.FriendService;
import com.sogifty.service.GiftService;
import com.sogifty.service.model.FriendModel;

@Path("friend")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FriendWebService {
	
	private FriendService friendService = new FriendService();
	private GiftService giftService = new GiftService();
	
	private static final Logger logger = Logger.getLogger(FriendWebService.class);
	
	@POST
	public Response create(Friend friend) {
		FriendModel returnedFriend = null;

		try {
			returnedFriend = friendService.create(friend);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedFriend).build();
	}
	
	@Path("{friendId}")
	@PUT
	public Response update(@PathParam("friendId") int id, Friend friend) {
		// Sets the right id to the friend to update
		friend.setId(id);
		FriendModel returnedFriend = null;

		try {
			returnedFriend = friendService.update(friend);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedFriend).build();
	}
	
	@Path("{friendId}")
	@DELETE
	public Response delete(@PathParam("friendId") int id, Friend friend) {
		// Sets the right id to the friend to delete
		friend.setId(id);

		try {
			friendService.delete(friend);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@Path("{friendId}/gifts")
	@GET
	public Response getGifts(@PathParam("friendId") int friendId) {
		logger.debug("Friend id : " + friendId);
		
		Set<Gift> returnedGifts = null;

		try {
			returnedGifts = giftService.getGifts(friendId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		
		return Response.ok(returnedGifts).build();
	}
}
