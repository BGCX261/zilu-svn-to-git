package com.zilu.context;

import javax.servlet.http.HttpSession;

public class SessionContext implements Context {

	private HttpSession session;
	
	public SessionContext(HttpSession session) {
		this.session = session;
	}
	
	public void add(String name, Object value) {
		session.setAttribute(name, value);
	}

	public Object get(String name) {
		return session.getAttribute(name);
	}
	
	public void remove(String name) {
		session.removeAttribute(name);
	}

}
