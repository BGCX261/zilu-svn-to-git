package com.zilu.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.zilu.util.Strings;
import com.zilu.util.file.IOFileUtil;

public class HttpUtil {
	
	public static byte[] getInputStreamContent(InputStream is) throws IOException {
        return IOFileUtil.getContent(is);
	}

	public static String paramsToBuffer(Map<String, Object> map, String requestEncode) {
		String params = Strings.mapToString(map, "&", "=");
		try {
			params = URLEncoder.encode(params, requestEncode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public static Map<String, Object> prepareParameter(ServletRequest req) {
		Map map = req.getParameterMap();
		Map<String, Object> params = new HashMap<String, Object>();
		for (Object key : map.keySet()) {
			Object value = map.get(key);
			String rValue = null;
			if (value instanceof String[]) {
				String[] values = (String[]) value;
				if (values.length > 1) {
					List<String> vl = new ArrayList<String>();
					for (String v : values) {
						if (!Strings.isEmpty(v)) {
							vl.add(v);
						}
					}
					params.put((String) key, vl);
					continue;
				}
				else {
					rValue = values[0];
				}
			}
			else {
				rValue = (String)value;
			}
			if (Strings.isEmpty(rValue)) {
				continue;
			}
			params.put((String) key, rValue);
		}
		return params;
	}
	
	/**
	 * 装载所有请求的参数，同时去掉左右空字符
	 * @param req
	 * @return
	 */
	public static Map<String, Object> prepareAllParameter(ServletRequest req) {
		Map map = req.getParameterMap();
		Map<String, Object> params = new HashMap<String, Object>();
		for (Object key : map.keySet()) {
			Object value = map.get(key);
			String rValue = null;
			if (value instanceof String[]) {
				String[] values = (String[]) value;
				if (values.length > 1) {
					List<String> vl = new ArrayList<String>();
					for (String v : values) {
						if (!Strings.isEmpty(v)) {
							vl.add(v);
						}
					}
					params.put((String) key, vl);
					continue;
				}
				else {
					rValue = values[0];
				}
			}
			else {
				rValue = (String)value;
			}
			rValue=rValue==null?"":rValue.trim();
			params.put((String) key, rValue);
		}
		return params;
	}
	
	/**
	 * 装载所有请求的参数，同时去掉左右空字符,并用去掉字段的类名
	 * @param req
	 * @param outPrefix
	 * @return
	 */
	public static Map<String, Object> prepareAllParameter(ServletRequest req, String outPrefix) {
		outPrefix = outPrefix+".";
		Map map = req.getParameterMap();
		Map<String, Object> params = new HashMap<String, Object>();
		for (Object key : map.keySet()) {
			Object value = map.get(key);
			String rValue = null;
			if (value instanceof String[]) {
				String[] values = (String[]) value;
				if (values.length > 1) {
					List<String> vl = new ArrayList<String>();
					for (String v : values) {
						if (!Strings.isEmpty(v)) {
							vl.add(v);
						}
					}
					params.put((String) key, vl);
					continue;
				}
				else {
					rValue = values[0];
				}
			}
			else {
				rValue = (String)value;
			}
			rValue=rValue==null?"":rValue.trim();
			String keyString = (String) key;
			if (keyString!=null && keyString.indexOf(outPrefix)==0){
				keyString = keyString.replace(outPrefix, "");
			}
			params.put(keyString, rValue);
		}
		return params;
	}
	
	/**
	 * 获取查询条件
	 * @param queryPrefix
	 * @param queryContidion
	 * @param req
	 * @return
	 */
	public static Map<String, Object> getContidionParameter(String queryPrefix,String[] queryCondition,ServletRequest req) {
		//Map map = req.getParameterMap();
		Map<String, Object> params = new HashMap<String, Object>();
		for (String key:queryCondition){
			String value = req.getParameter(queryPrefix+key);
			if ( value != null && !value.equals("") ){
				params.put(key, value.trim());
			}
		}
		return params;
	}
	
	/**
	 * 获取查询条件，并替换查询条件
	 * @param queryPrefix
	 * @param queryCondition
	 * @param req
	 * @param queryReplace
	 * @return
	 */
	public static Map<String, Object> getContidionParameter(String queryPrefix,String[] queryCondition,ServletRequest req,Map<String,String> queryReplace) {
		Map<String, Object> params = new HashMap<String, Object>();
		for (String key:queryCondition){
			String value = req.getParameter(queryPrefix+key);
			if ( value != null && !value.equals("") ){
				params.put(key, value.trim());
			}
		}
		if (queryReplace!=null){//要替换的查询条件
			for (Map.Entry<String,String> entry : queryReplace.entrySet()){
				params.remove(entry.getKey());
				params.put(entry.getKey(), entry.getValue());
			}
		}
		return params;
	}
	
	public static Object findCookie(HttpServletRequest req, String name) {
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie :cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 替换需要使用like查询条件的参数
	 * @param parameter
	 * @param likeCondition
	 * @return
	 */
	public static Map<String,Object> filterLikeCondition(Map<String,Object> parameter,String [] likeCondition){
		Map<String ,Object> newParameter = new HashMap<String,Object>();
		for(Map.Entry<String, Object> obj : parameter.entrySet()){
			newParameter.put(obj.getKey(), obj.getValue());
			String param = (String) obj.getValue();
			for(String condition : likeCondition) {
				if(obj.getKey().equals(condition)) {
					newParameter.put(obj.getKey(), "%"+param+"%");
				}
			}
		}
		return newParameter;
	}
	
	public static String getIpAddr(HttpServletRequest request) {    
	    String ip = request.getHeader("x-forwarded-for");    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	        ip = request.getHeader("Proxy-Client-IP");    
	    }    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	        ip = request.getHeader("WL-Proxy-Client-IP");    
	    }    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	        ip = request.getRemoteAddr();    
	    }    
	    return ip;    
	}  
	
}
