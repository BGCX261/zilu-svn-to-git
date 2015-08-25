package com.zilu.cipher;

public abstract class KeyCipher extends AbstractCipher {

	protected String key;
	
	public KeyCipher(String key) {
		this.key = key;
	}
}
