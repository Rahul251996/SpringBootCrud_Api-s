package com.etasens.exception;

public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserException(String e) {
		super(e);
	}

	public UserException(String message, Throwable ex) {
		super(message, ex);
	}

}
