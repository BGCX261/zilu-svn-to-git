package com.zilu.cipher;

public interface Decrypt {

	public String decrypt(String encodeStr) throws CipherException;

	public String getEncoding();

	public void setEncoding(String encoding);
}
