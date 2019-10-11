package com.ecm.core.exception;

public class AccessDeniedException  extends Exception {
	private static final long serialVersionUID = 1L;

	public AccessDeniedException() {
		super();
	}

	public AccessDeniedException(String arg0) {
		super(arg0);
	}

	public AccessDeniedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccessDeniedException(Throwable arg0) {
		super(arg0);
	}
}