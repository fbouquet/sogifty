package com.sogifty.exception;

public class UserException extends SogiftyException {
	private static final long serialVersionUID = 1L;
	public UserException() {}
	public UserException(String message) {
		super(message);
	}
}
