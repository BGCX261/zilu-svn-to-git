package com.zilu.face;

import com.zilu.http.HttpHelper;
import com.zilu.util.ReflectUtil;

public class BaseFace {
	
	String baseUrl;
	
	protected HttpHelper httpHelper;
	
	protected String getUrl() {
		String className = ReflectUtil.getShortName(getClass());
		int index = className.indexOf("Client");
		if (index != -1) {
			className = className.substring(0, index);
		}
		String methodName = ReflectUtil.getInvokeMethodName(2);
		if (methodName.equals("execute")) {
			return baseUrl + "/" + className + ".do";
		}
		else {
			return baseUrl + "/" + className + "/" + methodName + ".do";
		}
	}
	
	
}
