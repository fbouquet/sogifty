package com.sogifty.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sogifty.exception.SogiftyException;
import com.sogifty.service.ConfigurationService;
import com.sogifty.service.model.ConfigurationModel;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfigurationWebService {
	
	private ConfigurationService configurationService = new ConfigurationService();
	
	@Path("config")
	@GET
	public Response getConfiguration() {
		ConfigurationModel configuration = null;
		try {
			configuration = configurationService.getConfiguration();
		} catch (SogiftyException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
		return Response.ok(configuration).build();
	}
}
