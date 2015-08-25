package com.zilu.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

/**
 * Company: fsti
 * @author chenhm
 * Modify：
 * Description:
 */
public class Strings {

	public static String unqualify(String name) {
		return unqualify(name, '.');
	}

	public static String unqualify(String name, char sep) {
		return name.substring(name.lastIndexOf(sep) + 1, name.length());
	}

	public static boolean isEmpty(String string) {
		return string == null || string.trim().length() == 0;
	}
	
	public static boolean isEmpty(Integer in) {
		return in == null || in == 0;
	}
	
	public static boolean isEmpty(Long in) {
		return in == null || in == 0;
	}
	
	public static String nullIfEmpty(String string) {
		return isEmpty(string) ? null : string;
	}

	public static String emptyIfNull(String string) {
		return string == null ? "" : string;
	}

	public static String toString(Object component) {
		try {
			PropertyDescriptor[] props = Introspector.getBeanInfo(
					component.getClass()).getPropertyDescriptors();
			StringBuilder builder = new StringBuilder();
			for (PropertyDescriptor descriptor : props) {
				builder.append(descriptor.getName()).append("=").append(
						descriptor.getReadMethod().invoke(component)).append(
						"; ");
			}
			return builder.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static String[] split(String strings, String delims) {
		if (strings == null) {
			return new String[0];
		} else {
			StringTokenizer tokens = new StringTokenizer(strings, delims);
			String[] result = new String[tokens.countTokens()];
			int i = 0;
			while (tokens.hasMoreTokens()) {
				result[i++] = tokens.nextToken();
			}
			return result;
		}
	}
	
	public String concat(String[] strs, String delimiter) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String str : strs) {
			if (!first) {
				sb.append(delimiter);
			}
			else {
				first = false;
			}
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static String mapToString(Map<String, Object> map, String delimiter, String equals) {
		Set<Entry<String, Object>> entries = map.entrySet();
		if (entries == null || entries.isEmpty())
			return null;
		StringBuilder buffer = new StringBuilder();
		boolean notFirst = false;
		for (Iterator<Entry<String, Object>> it = entries.iterator(); it
				.hasNext();) {
			Entry<String, Object> entry = it.next();
			if (notFirst)
				buffer.append(delimiter);
			else
				notFirst = true;
			String value = String.valueOf(entry.getValue());
			buffer.append((String) entry.getKey()).append(equals).append(value.toString());
		}

		return new String(buffer);
	}
	
	public static String listToString (List<Object> list, String splitor) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object obj : list) {
			if (first) {
				first = false;
			}
			else {
				sb.append(splitor);
			}
			sb.append(String.valueOf(obj));
		}
		return sb.toString();
	}
	
	public static String arrayToString (Object [] arrayStr, String splitor) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		
		for(int i=0;i<arrayStr.length;i++) {
			if (first) {
				first = false;
			}
			else {
				sb.append(splitor);
			}
			sb.append(String.valueOf(arrayStr[i]));
		}
		
		return sb.toString();
	}
	
	public static List<String> stringToList(String str, String splitor) {
		String[] flags = split(str, splitor);
		List<String> list = new ArrayList<String>();
		for (String flag : flags) {
			list.add(flag);
		}
		return list;
	}
	
	public static Map<String, String> stringToMap(String paramStr, String delimiter, String equals) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		String[] paramPairs = split(paramStr, delimiter);
		for (String paramPair : paramPairs) {
			String[] param = split(paramPair, equals);
			if (param.length == 2) {
				paramsMap.put(param[0], param[1]);
			}
			else {
				paramsMap.put(param[0], paramPair.substring(param[0].length() + 1));
 			}
		}
		return paramsMap;
	}
	
	
	
	public static String toString(Object... objects) {
		return toString(" ", objects);
	}

	public static String toString(String sep, Object... objects) {
		if (objects.length == 0)
			return "";
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(sep).append(object);
		}
		return builder.substring(2);
	}

	/**
	 * 适配string 按给定的长度阶段，中文2个长度，英文1个长度
	 * @param str
	 * @param size
	 * @return
	 */
	public static String fitSize(String str, int size) {
		int s = 0;
		char ars[]= str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char a : ars) {
			byte[] bs = (String.valueOf(a)).getBytes();
			s += bs.length;
			if (s > size) {
				sb.append("...");
				break;
			}
			else {
				sb.append(a);
			}
		}
		
		return sb.toString();
	}
	
	public static String filHtmlfitSize(String input, int size) {  
        if (input == null || input.trim().equals("")) {  
            return "";  
        }
        // 去掉所有html元素,  
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
                "<[^>]*>", "");  
        str = str.replaceAll("[(/>)<]", "");  
        return fitSize(str,size);  
    } 
	
	public static String toClassNameString(String sep, Object... objects) {
		if (objects.length == 0)
			return "";
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(sep);
			if (object == null) {
				builder.append("null");
			} else {
				builder.append(object.getClass().getName());
			}
		}
		return builder.substring(2);
	}

	public static String toString(String sep, Class... classes) {
		if (classes.length == 0)
			return "";
		StringBuilder builder = new StringBuilder();
		for (Class clazz : classes) {
			builder.append(sep).append(clazz.getName());
		}
		return builder.substring(2);
	}

	public static String toString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	public static String capfirstChar(String msg) {
		char[] msgs = msg.toCharArray();
		msgs[0] = (char) (msgs[0] - 32);
		return String.valueOf(msgs);
	}
	
	public static String uncapfirstChar(String msg) {
		char[] msgs = msg.toCharArray();
		msgs[0] = (char) (msgs[0] + 32);
		return String.valueOf(msgs);
	}
	
	public static String getRandString(Long random,int len){
		java.util.Random rd = new java.util.Random(random);
		StringBuffer sb = new StringBuffer();
		int  rdGet;
		char  ch;
		for(int i = 0;i < len;i ++ ) {
		      rdGet = Math.abs(rd.nextInt()) % 10 + 48 ;// 产生48到57的随机数(0-9的键位值)   
		      //rdGet=Math.abs(rd.nextInt())%26+97;  // 产生97到122的随机数(a-z的键位值) 
		      ch = ( char ) rdGet;
	          sb.append( ch );
		}
		return sb.toString();
	}
	
	public static String toUnicode(String str) {
		str = str.replaceAll("&", "&amp;").replace("<", "&lt;").replaceAll(">", "&gt;")
			.replaceAll("\"", "&quot;");
		byte[] data = null;
//		fuck ！！！linux 和windows unicode的高位和低位竟然是相反的。。
		boolean isWindows = System.getProperty("os.name").indexOf("Windows")!= -1;
		try {
			data = str.getBytes("unicode");
		} catch (UnsupportedEncodingException e) {
		}
		if (data == null|| data.length <= 2) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for(int i=2;i<data.length;i+=2){
			int indexHigh,indexLow;
//			高低位判断
			if (isWindows) {
				indexHigh = i+1;
				indexLow = i;
			}
			else {
				indexHigh = i;
				indexLow = i+1;
			}
			if (data[indexHigh]  == 0&&data[indexHigh]<128){
//				基础ascii 不做编码转换
				sb.append((char)data[indexLow]);
			}
			else {
				String high = Integer.toHexString(data[indexHigh]&0xff);
				if (high.length() == 1) {
					high = "0"+ high;
				}
				String low = Integer.toHexString(data[indexLow]&0xff);
				if (low.length() == 1) {
					low = "0"+ low;
				}
				sb.append("&#x"+high+low+ ";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 把对象中的所有属性为NULL的值转化成""
	 * @param obj
	 * @return
	 */
	public static Object NullToString(Object obj){
	    Field[] field = obj.getClass().getDeclaredFields();
	    for (int i = 0; i < field.length; i++){
	    	if (field[i].getGenericType().toString().equals(String.class.toString())){//String类型
	    		try {
	    			field[i].setAccessible(true);
	    			if (field[i].get(obj)==null){
						field[i].set(obj, "");
	    			}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
	    	}else if (field[i].getGenericType().toString().equals(Integer.class.toString())){//Integer类型
	    		try {
	    			field[i].setAccessible(true);
	    			if (field[i].get(obj)==null){
						field[i].set(obj, 0);
	    			}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
	    	}/*else if (field[i].getGenericType().toString().equals(Date.class.toString())){//Date类型
	    		try {
	    			field[i].setAccessible(true);
	    			if (field[i].get(obj)==null){
						field[i].set(obj, new Date());
	    			}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
	    	}*/
		}
		return obj;
	}
	
	/**
	 * 对像转化成JSON格式的字符串
	 * @param obj
	 * @return
	 */
	public static String objectToJSON(Object obj){
		String returnValue = "{";
		if (obj!=null){
		    Field[] field = obj.getClass().getDeclaredFields();
		    for (int i = 0; i < field.length; i++){
		    	try {
	    			field[i].setAccessible(true);
	    			if (field[i].getName()!=null && field[i].get(obj)!=null){
	    				returnValue += field[i].getName()+":"+field[i].get(obj)+",";
	    			}
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		    if (returnValue.length()>1){
		    	returnValue = returnValue.substring(0,returnValue.length()-1);
		    }
		}
	    returnValue += "}";
		return returnValue;
	}
	
	public static void main(String[] args) {
		System.out.println(toUnicode("&<我是123"));
	}
}
