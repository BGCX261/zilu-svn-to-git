/***********************************************************************   
 *   
 *   BaseAct.java   
 *   
 *   @copyright     福建邮科通信 
 *   @creator       kangzhishan
 *   @email         kangzhisan@126.com    
 *   @create-time   	 2009-6-27   下午03:55:02   
 *   @revision        $Id:        
 ***********************************************************************/
package com.zilu.struts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.zilu.util.BeanUtils;

public abstract class BaseAction implements
		ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	protected final Logger logger = Logger.getLogger(getClass());
	
	
	public static final String SUCCESS = Action.SUCCESS;
	
	public static final String NONE = Action.NONE;
	
	public static final String ERROR = Action.ERROR;
	
	public static final String INPUT = Action.INPUT;
	
	public static final String LOGIN = Action.LOGIN;
	
	public static final String REDIRECT = "redirect";
	
	public static final String REDIRECT_POST = "redirectPost";
	
	public static final String FACE = "face";
	
	protected static final String MSG_HINT = "msg_hint";
	
	
	public <T extends Object> void populate(T t) {
		BeanUtils.populate(t, getRequest().getParameterMap());
	} 
	
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
        this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}	 
	
	public void setActionToChain(String action) {
		getRequest().setAttribute("actionChain", action);
	}
	
	public String getChainAction() {
		return (String) getRequest().getAttribute("actionChain");
	}
	
	public void setUrlToRedirect(String url) {
		getRequest().setAttribute("redirectUrl", url);
	}
	
	public void setPostParams(Map<String, Object> params) {
		getRequest().setAttribute("paramMap", params);
	}

	public String getRedirectUrl() {
		return (String) getRequest().getAttribute("redirectUrl");
	}
}
