package com.sogifty.exception;

import javax.ws.rs.core.Response;

public class SogiftyException extends Exception {
	private static final long serialVersionUID = 1L;
	private Response.Status status;
	
	public SogiftyException() {}
	
	public SogiftyException(Response.Status status) {
		super();
		this.status = status;
	}

	public Response.Status getStatus() {
		return status;
	}
}
