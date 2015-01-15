package com.sogifty.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.sogifty.dao.LoggingDAO;
import com.sogifty.exception.SogiftyException;

@Path("hello")
public class HelloPerson {

	@GET
	@Path("/{who}")
	public Response helloPerson(@PathParam("who") String who) throws SogiftyException {
		LoggingDAO.addLoggingData("HelloPerson : " + who + " contact HelloPerson web service.");
		return Response.status(200).entity("Hello " + who + "!").build();
	}
}
