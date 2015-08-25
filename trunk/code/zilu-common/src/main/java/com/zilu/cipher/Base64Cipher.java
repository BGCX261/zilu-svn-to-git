package com.zilu.cipher;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Company: fsti
 * @author chenhm
 * Date: Jan 13, 2010
 * Description: BASE64密码算法
 */
public class Base64Cipher extends AbstractCipher {
	
	private static final BASE64Encoder encoder = new BASE64Encoder();

	private static final BASE64Decoder decoder = new BASE64Decoder();
	
	public String decrypt(String encoded) throws CipherException {
		byte[] bt = null;
		try {
			bt = decoder.decodeBuffer(encoded);
			return new String(bt, getEncoding());
		} catch (IOException e) {
			throw new CipherException(e);
		}
		
	}

	public String encrypt(String rawStr) throws CipherException {
		try {
			return encoder.encode(rawStr.getBytes(getEncoding()));
		} catch (IOException e) {
			throw new CipherException(e);
		}
	}

}
