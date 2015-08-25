package com.zilu.config;

public class ZiluGlobal {
	
	
	public interface Config {
		public static final String QUERY_FILE_SUFFIX = ".sql";
	}
	
	public static class RespCode {
		
		private static final String MESSAGE_HEAD = "resp.code.";
		
		public static final String SUCCESS = "0";
		
		public static String getRespDesc(String code, String... params) {
			return code;
		}
		
		public static boolean isSuccess(String code) {
			return SUCCESS.equals(code);
		}
	}
}
