package com.sogifty.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.sogifty.service.LoggingService;

@Path("logs")
public class LoggingWebService {
	
	private LoggingService loggingService = new LoggingService();
	
	@GET
	public Response logging() {
		return Response.ok().entity(loggingService.getLoggingData()).build();
	}
	
	@GET
	@Path("clear")
	public Response clearLogs() {
		loggingService.clearLogs();
		return Response.ok("Logs cleared.").build();
	}
	
	@GET
	@Path("freq/{frequency}")
	public Response changeRefreshFrequency(@PathParam("frequency") int frequencyInSeconds) {
		loggingService.changeRefreshFrequency(frequencyInSeconds);
		return Response.ok(String.format("Refresh interval set to %d second(s).", frequencyInSeconds)).build();
	}
}
