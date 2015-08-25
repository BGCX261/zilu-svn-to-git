package com.zilu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Encoding {

	public static void printByEncode(String msg) throws UnsupportedEncodingException {
		System.out.println("I-G" + new String(msg.getBytes("ISO-8859-1"), "GBK"));
		System.out.println("I-U8" + new String(msg.getBytes("ISO-8859-1"), "UTF-8"));
		System.out.println("I-U" + new String(msg.getBytes("ISO-8859-1"), "unicode"));
		System.out.println("U8-G" + new String(msg.getBytes("UTF-8"), "GBK"));
		System.out.println("U8-I" + new String(msg.getBytes("UTF-8"), "ISO-8859-1"));
		System.out.println("U8-U" + new String(msg.getBytes("UTF-8"), "unicode"));
		System.out.println("G-U8" + new String(msg.getBytes("GBK"), "UTF-8"));
		System.out.println("G-I" + new String(msg.getBytes("GBK"), "ISO-8859-1"));
		System.out.println("G-U" + new String(msg.getBytes("GBK"), "unicode"));
		System.out.println("U-U8" + new String(msg.getBytes("unicode"), "UTF-8"));
		System.out.println("U-I" + new String(msg.getBytes("unicode"), "ISO-8859-1"));
		System.out.println("U-G" + new String(msg.getBytes("unicode"), "GBK"));
	}
	
	
	public static byte[] encode(String msg, String encode) throws UnsupportedEncodingException {
		return msg.getBytes(encode);
	}
	
	public static void printByte(byte[] encodes) {
		for (byte b : encodes) {
			System.out.print(b);
		}
	}
	
	public static String urlEnocde(String s, String encode) throws Exception {
		return URLEncoder.encode(s, encode);
	}
	
	public static void main(String[] args) throws Exception {
		String a = "拉丁香港台湾人民";
		a= new String(a.getBytes("GBK"), "ISO-8859-1");
		System.out.println(a);
		a= new String(a.getBytes("ISO-8859-1"), "UTF-8");
		a = new String(a.getBytes("UTF-8"), "GBK");
		System.out.println(a);
	}
}
