package com.zilu.struts;



import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zilu.face.ContextFacade;

public class ContextInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			ContextFacade.start(ServletActionContext.getRequest(), ServletActionContext.getResponse());
			return invocation.invoke();
		} finally {
			ContextFacade.clear();
		}
	}
	
}
