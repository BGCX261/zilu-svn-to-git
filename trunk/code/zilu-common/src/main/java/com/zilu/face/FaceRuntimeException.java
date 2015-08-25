package com.zilu.face;

public class FaceRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3050378996848566085L;
	
	private String code;
	
	public FaceRuntimeException(String code) {
		this.code = code;
	}

	
	public FaceRuntimeException(String code, Exception e) {
		super(e);
		this.code = code;
	}

	public FaceRuntimeException(String code, String msg) {
		super(msg);
		this.code = code;
	}


	public String getCode() {
		return code;
	}
}
