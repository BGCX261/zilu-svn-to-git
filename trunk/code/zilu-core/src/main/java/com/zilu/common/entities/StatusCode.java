package com.zilu.common.entities;

public enum StatusCode {
	
	NORMAL("N"),

	DELETE("D");
	
	private String value;
	
	private StatusCode(String code) {
		this.value = code;
	}
	
	public String getValue() {
		return value;
	}

}
