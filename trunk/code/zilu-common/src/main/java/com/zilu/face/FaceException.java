package com.zilu.face;

public class FaceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7674976331211681670L;

	private String code;
	
	public FaceException(String code) {
		this.code = code;
	}

	
	public FaceException(String code, Exception e) {
		super(e);
		this.code = code;
	}

	public FaceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
