package com.zilu.cipher;

import java.security.MessageDigest;

/**
 * Company: fsti
 * @author chenhm
 * Date: Jan 13, 2010
 * Description: 基于SHA256加密算法
 */
public class SHA256Cipher extends AbstractEncrypt {

	public String encrypt(String rawStr) throws CipherException {
		try {
			MessageDigest alg = MessageDigest.getInstance("SHA-256");
			alg.update(rawStr.getBytes(getEncoding()));
			byte[] userPwd = alg.digest();
			return CipherUtil.byte2hex(userPwd);
		} catch (Exception e) {
			throw new CipherException(e);
		} 
	}

}
