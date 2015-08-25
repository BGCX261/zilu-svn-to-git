package com.zilu.http;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class DebugUtil {
	
	public static void printHead(HttpServletRequest request) {
		Enumeration en = request.getHeaderNames();
		while(en.hasMoreElements()) {
			String name = (String) en.nextElement();
			String value = request.getHeader(name);
			System.out.println(name + " " + value);
		}
	}
}
