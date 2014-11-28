package com.sogifty.webservice;

import java.util.List;

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

import com.sogifty.exception.SogiftyException;
import com.sogifty.service.TagService;
import com.sogifty.service.model.TagModel;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagWebService {
	private TagService TagService = new TagService();
	
	@Path("tags")
	@GET
	public Response findAll() {
		List<TagModel> returnedTags = null;

		try {
			returnedTags = TagService.findAll(null);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedTags).build();
	}
	
	@Path("users/{userId}/friends/{friendId}/tags")
	@GET
	public Response findAllForFriend(@PathParam("friendId") int friendId) {
		List<TagModel> returnedTags = null;

		try {
			returnedTags = TagService.findAll(friendId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedTags).build();
	}
		
	@Path("tags")
	@POST
	public Response create(TagModel tag) {
		TagModel returnedTag = null;

		try {
			returnedTag = TagService.create(tag, null, null);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedTag.getId()).build();
	}
	
	@Path("users/{userId}/friends/{friendId}/tags")
	@POST
	public Response create(TagModel tag, @PathParam("userId") int userId, @PathParam("friendId") int friendId) {
		TagModel returnedTag = null;

		try {
			returnedTag = TagService.create(tag, userId, friendId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedTag.getId()).build();
	}
	
	@Path("tags/{tagId}")
	@PUT
	public Response update(@PathParam("tagId") int tagId, TagModel tag) {
		TagModel returnedTag = null;

		try {
			returnedTag = TagService.update(tagId, tag, null, null);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedTag.getId()).build();
	}
	
	@Path("users/{userId}/friends/{friendId}/tags/{tagId}")
	@PUT
	public Response update(@PathParam("tagId") int tagId, TagModel tag, @PathParam("userId") int userId, @PathParam("friendId") int friendId) {
		TagModel returnedTag = null;

		try {
			returnedTag = TagService.update(tagId, tag, userId, friendId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(returnedTag.getId()).build();
	}
	
	@Path("tags/{tagId}")
	@DELETE
	public Response delete(@PathParam("tagId") int id) {
		try {
			TagService.delete(id, null, null);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@Path("users/{userId}/friends/{friendId}/tags/{tagId}")
	@DELETE
	public Response delete(@PathParam("tagId") int id, @PathParam("userId") int userId, @PathParam("friendId") int friendId) {
		try {
			TagService.delete(id, userId, friendId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
}
