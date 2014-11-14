package com.sogifty.exception;

import javax.ws.rs.core.Response;

public class UserException extends SogiftyException {
	private static final long serialVersionUID = 1L;
	private Response.Status status;
	
	public UserException() {}
	
	public UserException(Response.Status status) {
		super();
		this.status = status;
	}

	public Response.Status getStatus() {
		return status;
	}
}
