package com.zilu.cipher;

import java.security.MessageDigest;

public class SHA1Cipher extends AbstractEncrypt {

	public String encrypt(String rawStr) throws CipherException {
		try {
			MessageDigest alg = MessageDigest.getInstance("SHA-1");
			alg.update(rawStr.getBytes(getEncoding()));
			byte[] userPwd = alg.digest();
			return CipherUtil.byte2hex(userPwd);
		} catch (Exception e) {
			throw new CipherException(e);
		}
	}

}
