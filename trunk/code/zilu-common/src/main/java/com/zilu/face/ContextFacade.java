package com.zilu.face;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zilu.context.SessionContext;




public class ContextFacade {
	
	private static ThreadLocal<ContextFacade> local = new ThreadLocal<ContextFacade>();
	
	private HttpServletRequest req;
	
	private HttpServletResponse resp;
	
	private SessionContext sessionContext;
	
	public static void start(HttpServletRequest req, HttpServletResponse resp) {
		get().req = req;
		get().resp = resp;
		get().sessionContext = new SessionContext(req.getSession());
	}
	
	public static ContextFacade get() {
		ContextFacade facade = local.get();
		if (facade == null) {
			facade = new ContextFacade();
			local.set(facade);
		}
		return facade;
	}
	
	public static SessionContext sessionContext() {
		return get().sessionContext;
	}
	
	public static void clear() {
		ContextFacade facade = local.get();
		if (facade != null) {
			facade.req = null;
			facade.resp = null;
			facade.sessionContext = null;
		}
		local.set(null);
	}
	
	public static HttpServletRequest getRequest() {
		return get().req;
	}
	
	public static HttpServletResponse getResponse() {
		return get().resp;
	}
	
	public static ServletContext getServletContext() {
		if (getSession() == null) {
			return null;
		}
		return getSession().getServletContext();
	}
	
	public static HttpSession getSession() {
		if (getRequest() == null) {
			return null;
		}
		return getRequest().getSession();
	}
	
	public static boolean isAjaxRequest() {
		return getRequest().getHeader("x-requested-with") != null;
	}
	
}
