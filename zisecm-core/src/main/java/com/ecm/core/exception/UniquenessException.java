package com.ecm.core.exception;
/**
 * 唯一性检查异常
 * @Title:
 * @author Haihong Rong
 * @date:   2020-9-16 14:23:09 
 * @Description:
 */
public class UniquenessException  extends Exception {
	private static final long serialVersionUID = 1L;

	public UniquenessException() {
		super();
	}

	public UniquenessException(String arg0) {
		super(arg0);
	}

	public UniquenessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UniquenessException(Throwable arg0) {
		super(arg0);
	}
}