package com.jvscapture.datasource.ExceptionHandlers;

public class ConnectionException extends Exception {
	private static final long serialVersionUID = 1L;
	public ConnectionException(String msg) {
		super(msg);
	}
}
