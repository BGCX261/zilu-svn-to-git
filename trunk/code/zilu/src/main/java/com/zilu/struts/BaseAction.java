package com.zilu.struts;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.zilu.config.ConfigFacade;
import com.zilu.config.ZiluGlobal;
import com.zilu.config.ZiluGlobal.RespCode;
import com.zilu.face.ContextFacade;
import com.zilu.util.BeanUtils;
import com.zilu.util.Strings;

public abstract class BaseAction {

	protected final Logger logger = Logger.getLogger(getClass());
	
	
	public static final String SUCCESS = Action.SUCCESS;
	
	public static final String NONE = Action.NONE;
	
	public static final String ERROR = Action.ERROR;
	
	public static final String INPUT = Action.INPUT;
	
	public static final String LOGIN = Action.LOGIN;
	
	public static final String CHAIN_ACTION = "chainAction";
	
	public static final String REDIRECT = "redirect";
	
	public static final String REDIRECT_POST = "redirectPost";
	
	public static final String REDIRECT_ACTION = "redirectAction";
	
	public static final String FACE = "face";
	
	public static final String PAGE = "page";
	
	protected static final String MSG_HINT = "msg_hint";
	
	public <T extends Object> void populate(T t) {
		BeanUtils.populate(t, getRequest().getParameterMap());
	} 
	
	public void setError(String errorCode, String... msg) {
		String errorMsg = RespCode.getRespDesc(errorCode);
		if (errorMsg == null) {
			errorMsg = errorCode;
		}
		if (msg.length==1){
			if (!Strings.isEmpty(msg[0])) {
				errorMsg += ":"+msg[0];
			}
		}
		ContextFacade.getRequest().setAttribute("msg_hint", errorMsg);
	}
	
	public HttpServletRequest getRequest() {
		return ContextFacade.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ContextFacade.getResponse();
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
	
	public void setActionToRedirect(String action) {
		getRequest().setAttribute("actionRedirect", action);
	}
	
	public String getRedirectAction() {
		return (String) getRequest().getAttribute("actionRedirect");
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
	
	public String sendChainAction(String action) {
		setActionToChain(action);
		return CHAIN_ACTION;
	}
	
	public String sendRedirect(String url) {
		setUrlToRedirect(url);
		return REDIRECT;
	}
	
	public String sendRedirectPost(String url, Map<String, Object> params) {
		setPostParams(params);
		setUrlToRedirect(url);
		return REDIRECT_POST;
	}
	
	public String sendPage(String url) {
		setUrlOfPage(url);
		return PAGE;
	}
	
	public void writeResult(String result) throws IOException {
		String encoding = ConfigFacade.getValue("http.encoding"); 
		getResponse().setCharacterEncoding(encoding);
		Writer w = getResponse().getWriter();
		w.write(result);
		w.close();
	}
	
	public void setUrlOfPage(String url) {
		getRequest().setAttribute("pageUrl", url);
	}
	
	public String getPageUrl() {
		return (String)getRequest().getAttribute("pageUrl");
	}
	
	public String sendError(String errorCode){
		if (ContextFacade.isAjaxRequest()) {
			ZiluResponce errorResp = new ZiluResponce();
			errorResp.setRespCode(errorCode);
			errorResp.setRespDesc(ZiluGlobal.RespCode.getRespDesc(errorCode));
			ResultHandler.instance().setResponce(errorResp);
			return FACE;
		}
		else {
			setError(errorCode);
			return ERROR;
		}
	}
	
	public String sendError(String errorCode, String desc) {
		if (Strings.isEmpty(desc)) {
			desc = RespCode.getRespDesc(errorCode);
		}
		else {
			desc = RespCode.getRespDesc(errorCode) + "：" + desc;
		}
		
		if (ContextFacade.isAjaxRequest()) {
			ZiluResponce errorResp = new ZiluResponce();
			errorResp.setRespCode(errorCode);
			errorResp.setRespDesc(desc);
			ResultHandler.instance().setResponce(errorResp);
			return FACE;
		}
		else {
			ContextFacade.getRequest().setAttribute("msg_hint", desc);
			return ERROR;
		}
	}
	
}
