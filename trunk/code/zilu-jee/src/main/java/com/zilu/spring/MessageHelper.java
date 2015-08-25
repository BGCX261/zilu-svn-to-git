package com.zilu.spring;


import java.util.Locale;

public class MessageHelper {

	public static String get(String name, String... paramter) {
		try {
			return SpringUtil.getContext().getMessage(name, paramter, getDefaultLocale());
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Locale getDefaultLocale() {
		return Locale.CHINA;
	}
	
	public static void main(String[] args) {
		System.out.println(get("resp.code.1003"));
	}
}
