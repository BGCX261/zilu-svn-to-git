package com.zilu.sql;

public class SqlException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6621430906392918222L;

	public SqlException(String msg) {
		super(msg);
	}
	
	public SqlException(Exception e) {
		super(e);
	}
}
