package com.zilu.util.mail;

public class MailException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2416045114427057354L;

	public MailException(String msg) {
		super(msg);
	}
	
	public MailException(Throwable t)
	{
		super(t);
		this.setStackTrace(t.getStackTrace());
	}
	
	public MailException(String message, Throwable t)
	{
		super(message, t);
		this.setStackTrace(t.getStackTrace());
	}
}
