package com.zilu.cipher;

public abstract class AbstractEncrypt implements Encrypt{

	public static String DEFAULT_ENCODE = "UTF-8";

	private String encoding = DEFAULT_ENCODE;

	public AbstractEncrypt() {
	}

	public AbstractEncrypt(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
