package com.zilu.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.zilu.util.Strings;

/**
 * Company: fsti
 * @author chenhm
 * Date: Jan 13, 2010
 * Description: 基于3DES的密码算法
 */
public class DESCipler extends KeyCipher {

	public DESCipler(String key) {
		super(key);
	}

	public String encrypt(String encoded) throws CipherException {
		if(Strings.isEmpty(encoded)){
			return "";
		}
		
		String outStr = null;
		byte[] inBytes = null;
		try {
			inBytes = encoded.getBytes(getEncoding());
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "DESede"));
			byte[] outbytes = cipher.doFinal(inBytes);
			outStr = CipherUtil.byte2hex(outbytes);
		}  catch (Exception e) {
			throw new CipherException(e);
		}
		return outStr;
	}

	public String decrypt(String rawStr) throws CipherException {
		if(Strings.isEmpty(rawStr)){
			return "";
		}
		
		String outStr = null;
		byte[] inBytes = null;
		try {
			inBytes = CipherUtil.hex2byte(rawStr);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "DESede"));
			byte[] outbytes = cipher.doFinal(inBytes);
			outStr = new String(outbytes, getEncoding());
			return outStr;
		} catch (Exception e) {
			throw new CipherException(e);
		}
		
	}

}
