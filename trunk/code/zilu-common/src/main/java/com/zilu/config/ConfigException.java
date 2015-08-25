package com.zilu.config;

public class ConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8346781402348606224L;

	public ConfigException(String msg) {
		super(msg);
	}
	
	public ConfigException(Throwable t) {
		super("error load config", t);
	}
	
	public ConfigException(String msg, Throwable t) {
		super(msg, t);
	}
}
