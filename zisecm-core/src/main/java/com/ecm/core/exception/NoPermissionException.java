package com.ecm.core.exception;

public class NoPermissionException  extends Exception {
	private static final long serialVersionUID = 1L;

	public NoPermissionException() {
		super();
	}

	public NoPermissionException(String arg0) {
		super(arg0);
	}

	public NoPermissionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoPermissionException(Throwable arg0) {
		super(arg0);
	}

	public NoPermissionException(int serviceCode, int actionCode, String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.serviceCode = serviceCode;
		this.actionCode = actionCode;
	}
	
	private int serviceCode;
	public int getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(int serviceCode) {
		this.serviceCode = serviceCode;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	private int actionCode;
}