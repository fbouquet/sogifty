package com.sogifty.exception;

public class SogiftyException extends Exception {
	private static final long serialVersionUID = 1L;
	public SogiftyException() {}
	public SogiftyException(String message) {
		super(message);
	}
}
