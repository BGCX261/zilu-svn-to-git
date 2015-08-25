package com.zilu.util.mail;


public class MailServer {
	
	private static final int DEFAULT_POP3_PORT = 110;
	private static final int DEFAULT_SMTP_PORT = 25;
	private static final String DEFAULT_CHARSET = "GBK";

//	pop服务器地址
	private String popHost;
	
//	pop服务器端口
	private int popPort = DEFAULT_POP3_PORT;
	
//	stmp服务器地址
	private String smtpHost;
	
//	stmp服务器端口
	private int smtpPort = DEFAULT_SMTP_PORT;
	
//	服务器编码
	private String charset = DEFAULT_CHARSET;
	
	private String addrRegex;
	
	public void define(String regex) {
		addrRegex = regex;
	}
	
	public boolean matchs(String address) {
		return address.matches(addrRegex);
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}


	public String getPopHost() {
		return popHost;
	}

	public void setPopHost(String popHost) {
		this.popHost = popHost;
	}

	public int getPopPort() {
		return popPort;
	}

	public void setPopPort(int popPort) {
		this.popPort = popPort;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

}
