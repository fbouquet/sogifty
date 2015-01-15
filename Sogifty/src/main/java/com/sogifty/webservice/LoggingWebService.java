package com.sogifty.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.sogifty.service.LoggingService;

@Path("logging")
public class LoggingWebService {
	
	private LoggingService loggingService = new LoggingService();
	
	@GET
	public Response logging() {
		return Response.status(200).entity(loggingService.getLoggingData()).build();
	}
	
	@GET
	@Path("clear")
	public Response clearLogs() {
		loggingService.clearLogs();
		return Response.status(200).build();
	}
}
