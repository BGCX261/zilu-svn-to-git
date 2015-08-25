package com.zilu.cipher;

import java.security.MessageDigest;

/**
 * Company: fsti
 * @author chenhm
 * Date: Jan 13, 2010
 * Description: MD5加密算法
 */
public class MD5Cipher extends AbstractEncrypt {

	public String encrypt(String rawStr) throws CipherException {
		try {
			MessageDigest alg;
			alg = MessageDigest.getInstance("MD5");
			alg.update(rawStr.getBytes(getEncoding()));
			byte[] userPwd = alg.digest();
			return CipherUtil.byte2hex(userPwd);
		} catch (Exception e) {
			throw new CipherException(e);
		}
	}

}
