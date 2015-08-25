package com.zilu.util;




import com.zilu.cipher.Base64Cipher;
import com.zilu.cipher.CipherException;
import com.zilu.cipher.DESCipler;
import com.zilu.cipher.MD5Cipher;
import com.zilu.cipher.RSAEncrypt;
import com.zilu.cipher.SHA1Cipher;
import com.zilu.cipher.SHA256Cipher;
import com.zilu.util.file.DBKeyCreator;


/**
 * Company: fsti
 * @author chenhm
 * Date: Jan 13, 2010
 * Description: 一些常用算法的工具类
 */
public final class Algorithm {

	private static Base64Cipher base64Cipher = new Base64Cipher();
	
	private static SHA1Cipher sha1Cipher = new SHA1Cipher();
	
	private static SHA256Cipher sha256Cipher = new SHA256Cipher();
	
	private static MD5Cipher md5Cipher = new MD5Cipher();
	/**
	 * Base64编码
	 * 
	 * @param encoded
	 * @return
	 */
	final public static String base64Decode(String encoded) {
		try {
			return base64Cipher.decrypt(encoded);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * Base64解码
	 * 
	 * @param plainText
	 * @return
	 */
	final public static String base64Encode(String plainText) {
		try {
			return base64Cipher.encrypt(plainText);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * SHA-1加密算法
	 * 
	 * @param string
	 * @return
	 */
	public static String sha1Encrypt(String string) {
		try {
			return sha1Cipher.encrypt(string);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MD5加密算法
	 * 
	 * @param string
	 * @return
	 */
	public static String md5Encrypt(String string) {
		try {
			return md5Cipher.encrypt(string);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SHA-256加密算法
	 * 
	 * @param string
	 * @return
	 */
	public static String sha256Encrypt(String string) {
		try {
			return sha256Cipher.encrypt(string);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int stringHashCode(String code) {
		byte[] arrays = code.getBytes();
		int hashCode = 0;
		int i = 1;
		for (byte b : arrays) {
			if (hashCode > Integer.MAX_VALUE) {
				break;
			}
			hashCode = b * 2 * i;
			i++;
		}
		return hashCode;
	}
	
	
	/**
	 * 3DES加密
	 * @param inStr
	 * @param key
	 * @return
	 */
	public static String encriptDES(String inStr, String key) {
		try {
			return new DESCipler(key).encrypt(inStr);
		} catch (CipherException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 3DES解密
	 * @param inStr
	 * @param key
	 * @return
	 */
	public static String decriptDES(String inStr, String key) {
		try {
			return new DESCipler(key).decrypt(inStr);
		} catch (CipherException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String encriptRSA(String inStr, String privateKey) {
		RSAEncrypt encrypt = new RSAEncrypt(privateKey);
		try {
			return encrypt.encrypt(inStr);
		} catch (CipherException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static boolean validRSASign(String inStr, String sign, String publicKey) {
		try {
			return RSAEncrypt.validate(inStr, sign, publicKey);
		}catch (CipherException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String key = "2ZBV7YX3RK0IBPRHG5C527QZ";
		String raw = "kjjxjy" + "201008261048389057" + "100" + "null" + "20100826104847" + "20100827104847";
		String r = encriptDES(raw, key);
		System.out.println(r);
		System.out.println(DBKeyCreator.getRandomKey(12));
		System.out.println(encriptDES("http://www.4g118114.com/","111111111111111111111111"));
		System.out.println(md5Encrypt("fsti_wuyi"));
	}
}