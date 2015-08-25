package com.zilu.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class FaceResponce {

	Map<String, String> headers = new HashMap<String, String>();
	
	byte[] respData;
	
	String encode;

	public Map<String, String> getHeaderMap() {
		return headers;
	}

	public byte[] getRespData() {
		return respData;
	}

	public String getEncode() {
		return encode;
	}
	
	public String getString() {
		try {
			String s = new String(respData, encode);
			return s;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getPainString() {
		return getString().replaceAll("(\n|\t|\r|  )", "");
	}
	
}
