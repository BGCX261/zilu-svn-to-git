package com.zilu.struts;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.zilu.config.ConfigFacade;
import com.zilu.face.ContextFacade;
import com.zilu.face.adapter.AdapterFacade;
import com.zilu.util.PrimitiveUtil;

public class ResultHandler {
	
	private ResultHandler(){}
	
	private static ResultHandler jsonHandler = new ResultHandler();
	
	private static ResultHandler xmlResult = new ResultHandler();
	
	private static String RESPONCE_KEY = "responce";
	
	private String type = ResultType.JSON;
	
	public static class ResultType {
		
		public final static String JSON = "json";
		
		public final static String XML = "xml";
		
		public final static String TEXT = "text";
		
		private static Map<String, String> typeMap = new HashMap<String, String>();
		
		static {
			typeMap.put(JSON, "text/javascript");
			typeMap.put(XML, "text/xml");
			typeMap.put(TEXT, "text/plain");
		}
		
		public static String getType(String type) {
			return typeMap.get(type);
		}
	}
	
	public static ResultHandler instance() {
		return jsonHandler();
	}
	
	public static ResultHandler jsonHandler() {
		return jsonHandler;
	}
	
	public static ResultHandler xmlHandler() {
		return xmlResult;
	}
	
	public void setResponce(Object obj) {
		ContextFacade.getRequest().setAttribute(RESPONCE_KEY, obj);
	}
	
	public Object getResponce() {
		return ContextFacade.getRequest().getAttribute(RESPONCE_KEY);
	}
	
	public void doResponce() {
		Object obj = ResultHandler.instance().getResponce();
		if (obj == null) {
			throw new RuntimeException("no responce set in request");
		}
		doResponce(obj);
	}
	
	public void doStringResponce(String result, String type) {
		HttpServletResponse resp = ContextFacade.getResponse();
		try {
			String encode = ConfigFacade.getValue("http.encoding");
			resp.setContentType(ResultType.getType(type) + " charset=" + encode);
			System.out.println("result: " + result);
			PrintWriter w = resp.getWriter();
			w.write(result);
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doResponce(Object obj) {
		String result = null;
		boolean primitive = false;
		if (!PrimitiveUtil.isPrimitiveOrWrapper(obj.getClass())
				&& obj instanceof String) {
			primitive = true;
		}
		if (type == ResultType.JSON) {
			result = primitive? String.valueOf(obj): AdapterFacade.instance().jsonAdapter().wrap(obj);
		}
		else if(type == ResultType.XML) {
			result = String.valueOf(obj);
		}
		doStringResponce(result, type);
	}
	
}
