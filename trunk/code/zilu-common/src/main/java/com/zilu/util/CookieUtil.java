/**
 * 
 */
package com.zilu.util;

import javax.servlet.http.Cookie;

/**
 * @author ywj
 *
 */
public class CookieUtil {

	/**
	 * 移除COOKIE
	 * @param cookieName
	 * @param cookiePath
	 * @return
	 */
	public Cookie removeCookie(String cookieName,String cookiePath){
		Cookie cookie = new Cookie(cookieName,"");
		cookie.setPath(cookiePath);
		cookie.setMaxAge(0);//立即删除
		return cookie;
	}
	
	/**
	 * 从COOKIE移除部分结果值	默认保存一个月
	 * @param cookies
	 * @param cookieName
	 * @param cookiePath
	 * @param linkIdArray
	 * @return
	 */
	public Cookie removePartCookie(Cookie[] cookies,String cookieName,String cookiePath,String[] linkIdArray){
		String linkIds = getCookieValue(cookies, cookieName);
		int size = linkIdArray.length;
		for (int j=0;j<size;j++){
			String linkId = linkIdArray[j]+"#";
			if(linkId.length()>1 && linkIds.indexOf(linkId)>=0){
				linkIds = linkIds.replace(linkId, "");
			}
		}
		return addObjectToCookie(cookieName, cookiePath, linkIds);
	}
	
	/**
	 * 从COOKIE移除部分结果值
	 * @param cookies
	 * @param cookieName
	 * @param cookiePath
	 * @param linkIdArray
	 * @param age
	 * @return
	 */
	public Cookie removePartCookie(Cookie[] cookies,String cookieName,String cookiePath,String[] linkIdArray,int age){
		String linkIds = getCookieValue(cookies, cookieName);
		int size = linkIdArray.length;
		for (int j=0;j<size;j++){
			String linkId = linkIdArray[j]+"#";
			if(linkId.length()>1 && linkIds.indexOf(linkId)>=0){
				linkIds = linkIds.replace(linkId, "");
			}
		}
		return addObjectToCookie(cookieName, cookiePath, linkIds, age);
	}
	
	/**
	 * 更新COOKIE缓存  默认保存一个月
	 * @param cookieName
	 * @param cookiePath
	 * @param linkIds
	 * @return
	 */
	public Cookie addObjectToCookie(String cookieName,String cookiePath,String linkIds){
		Cookie cookie = new Cookie(cookieName,linkIds);
		cookie.setPath(cookiePath);
		cookie.setMaxAge(30*24*60*60);//缓存一个月
		return cookie;
	}

	/**
	 * 更新COOKIE缓存
	 * @param cookieName
	 * @param cookiePath
	 * @param linkIds
	 * @param age
	 * @return
	 */
	public Cookie addObjectToCookie(String cookieName,String cookiePath,String linkIds,int age){
		Cookie cookie = new Cookie(cookieName,linkIds);
		cookie.setPath(cookiePath);
		cookie.setMaxAge(age);//缓存时间
		return cookie;
	}
	
	/**
	 * 获取指定COOKIE对应的值
	 * @param cookies
	 * @param cookieName
	 * @return
	 */
	public String getCookieValue(Cookie[] cookies,String cookieName){
		String resultValue = "";
		int len = cookies==null?0:cookies.length;
		for (int i=0;i<len;i++){
			if (cookieName.equals(cookies[i].getName())){
				resultValue = cookies[i].getValue();
				break;
			}
		}
		return resultValue;
	}
}
