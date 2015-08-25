package com.zilu.util;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	/**
	 * 返回匹配正则表达式的给定参数的值
	 * 
	 * @param pageData
	 * @param regex 正则表达式
	 * @param arg 要返回正则表达式中的哪个参数值
	 * @return 返回arg给定参数的值
	 */
	public static String getMatchData(String pageData, String regex, String arg) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pageData);
		if (matcher.find()) {
			String strContent1 = matcher.group();
			Matcher matcher1 = pattern.matcher(strContent1);
			strContent1 = matcher1.replaceAll(arg);
			return strContent1;
		}
		return "";
	}

	public static List<String> getMatchDataList(String data, String regex,
			String arg) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		List<String> dataList = new ArrayList<String>();
		while (matcher.find()) {
			String strContent = matcher.group();
			Matcher matcher1 = pattern.matcher(strContent);
			strContent = matcher1.replaceAll(arg);
			dataList.add(strContent);
		}
		return dataList;
	}

	public static boolean contain(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean match(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String fixNumber(String number) {
		return number.trim().replaceAll("^0*", "");
	}
	
	public static boolean checkAreaCode (String areaCode) {
		if(Strings.isEmpty(areaCode)){
			return false;
		}
		else {
			return match(areaCode,"^\\d{3,4}$");
		}
	}
	
	public static boolean checkIdNo (String idCardNo) {
		if(Strings.isEmpty(idCardNo)){
			return false;
		}
		else {
			return match(idCardNo,"^\\d{15}$|^\\d{17}([0-9]|X|x)$");
		}
	}
	
	public static  boolean checkMobile(String mobile) {
		if(Strings.isEmpty(mobile)){
			return false;
		}else{
			return match(mobile,"^[1][358][0-9]{9}$|^\\d{11,12}$");
		}
	}
	public static boolean checkEmail(String email) {
		if(Strings.isEmpty(email)){
			return false;
		}else{
			return match(email,"^([\\w]+)(.[\\w]+)*@([\\w-]+\\.){1,5}([A-Za-z]){2,4}$");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(RegexUtil.match("xzz", "[X|x][Z|z]\\d*"));
	}
}
