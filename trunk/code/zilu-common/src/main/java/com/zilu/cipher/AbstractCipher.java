package com.zilu.cipher;

public abstract class AbstractCipher implements Cipher {

	public static String DEFAULT_ENCODE = "UTF-8";

	private String encoding = DEFAULT_ENCODE;

	public AbstractCipher() {
	}

	public AbstractCipher(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
