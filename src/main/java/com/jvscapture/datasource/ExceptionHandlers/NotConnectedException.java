package com.jvscapture.datasource.ExceptionHandlers;

public class NotConnectedException extends Exception {
	private static final long serialVersionUID = 1L;
	NotConnectedException(String msg) {
		super(msg);
	}

}
