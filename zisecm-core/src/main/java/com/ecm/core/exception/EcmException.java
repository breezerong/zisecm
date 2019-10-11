package com.ecm.core.exception;
/**
 * ECM异常
 * @author Haihong Rong
 *
 */
public class EcmException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EcmException(String msg) {
		super(msg);
	}
	public EcmException(int serviceCode,int actionCode,String message) {
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
