package com.zilu.cipher;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAEncrypt extends AbstractEncrypt {

	private String privateKey;
	
	public RSAEncrypt(String privateKey) {
		this.privateKey = privateKey;
	}

	public String encrypt(String rawStr) throws CipherException {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					CipherUtil.hex2byte(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
			Signature signet = Signature.getInstance("MD5withRSA");
			signet.initSign(myprikey);
			signet.update(rawStr.getBytes());
			byte[] signed = signet.sign();
			return CipherUtil.byte2hex(signed);
		} catch (Exception e) {
			throw new CipherException(e);
		}
	}

	public static boolean validate(String rawStr, String signStr, String publicKey) throws CipherException {
		try {
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
					CipherUtil.hex2byte(publicKey));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			byte[] signed = CipherUtil.hex2byte(signStr);
			Signature signetcheck = Signature.getInstance("MD5withRSA");
			signetcheck.initVerify(pubKey);
			signetcheck.update(rawStr.getBytes());
			return signetcheck.verify(signed);
		} catch (Exception e) {
			throw new CipherException(e);
		}
	}
	
	public static void main(String args[]) throws CipherException {
		String privateKey="30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100924bf0e2c1d09ffda66cc7171a7039e8a06a61dddd893a3e465d68beab78339d96ae66390d3fc48a9772769623f43baad4fd8ff6f7e2147be19332130e149b999b586d02d2a2bb97a45b41a504c5b17f814f91895090f13416d6a830fde061f87e3b023e7c846ba56df24a7de649c9dce99ef4d2753f558ad191f71209b4a74702030100010281804c99bcc04e7139446bbeab23fab0e6715a8539471d373c2add570d80407208bcabe817182a4ac85334e73716782eea8b1148cef7a665570a625b5b2dc5029f51aa0d8565f4285fe76ce1c9e3dd05697882f0e9bd92af4ac8d8e0646d7185adefdcaccf8972360102457ba67c1c5911f71323afccb52a2ace6a1243b27314e2f1024100e505552f22a429e9ed06b29e283a3dc7c90b424fe06c6916642ed30d4eae69733ffcdecd47d1c22853c7534fb2c266f470fb28d8810e58058f43d026419b075f024100a387dd24040a25a00b02c5efea0f8c3b95badeef93f5e8a89c6fe2b4142789b19add5e9029161943a3ad333cd50910fa61f47169b597cd044f08f1d21eb07119024100c5ee15f0e6bf7a79f1a0183f18053ddfaca14e6e3a47778b228a555ceae351bf894dc2412810e0fc796b8b5515e96d915513bbf9619044028a0ed1963e9cfd81024022664014856d0501278fcbc76dfb1b4aa5728bd6e5cf02c405345c01f9835fa9a6a40e7250d23f3a4541f7d74211082b4353c24fb44d5c691d9f0a0f633946710241009410ad633c4b1a2fcaa70decc776c11bc88c0d4dcbb38919247ccbed1ebbf0f41b27506d48f5acd74f5f908b345570bdc80cc242cd503faef894a8979f8f4079";
		String publicKey="30819f300d06092a864886f70d010101050003818d0030818902818100924bf0e2c1d09ffda66cc7171a7039e8a06a61dddd893a3e465d68beab78339d96ae66390d3fc48a9772769623f43baad4fd8ff6f7e2147be19332130e149b999b586d02d2a2bb97a45b41a504c5b17f814f91895090f13416d6a830fde061f87e3b023e7c846ba56df24a7de649c9dce99ef4d2753f558ad191f71209b4a7470203010001";
		String rawStr = "1234567";
		RSAEncrypt rsa = new RSAEncrypt(privateKey);
		String sign = rsa.encrypt(rawStr);
		System.out.println(sign);
		System.out.println(rsa.validate(rawStr, sign, publicKey));
	}
	
}
