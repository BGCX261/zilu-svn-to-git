package com.zilu.cipher;

public interface Encrypt {

	public String encrypt(String rawStr) throws CipherException;

	public String getEncoding();

	public void setEncoding(String encoding);
}
