package com.sogifty.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sogifty.exception.SogiftyException;
import com.sogifty.service.TagService;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagWebService {
	private TagService tagService = new TagService();
		
	@Path("tags/{tagId}")
	@DELETE
	public Response delete(@PathParam("tagId") int id) {
		try {
			tagService.delete(id, null, null);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@Path("users/{userId}/friends/{friendId}/tags/{tagId}")
	@DELETE
	public Response delete(@PathParam("tagId") int id, @PathParam("userId") int userId, @PathParam("friendId") int friendId) {
		try {
			tagService.delete(id, userId, friendId);
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
}
