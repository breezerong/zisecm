package com.ecm.core.exception;
/**
 * SQL 语法拒绝
 * @author Haihong Rong
 * Date:2020年3月27日 上午8:59:25
 */
public class SqlDeniedException  extends Exception {
	private static final long serialVersionUID = 1L;

	public SqlDeniedException() {
		super();
	}

	public SqlDeniedException(String arg0) {
		super(arg0);
	}

	public SqlDeniedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SqlDeniedException(Throwable arg0) {
		super(arg0);
	}
}