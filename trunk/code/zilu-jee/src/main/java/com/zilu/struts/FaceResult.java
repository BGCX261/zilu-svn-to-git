package com.zilu.struts;



import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;
import com.zilu.web.ResultHandler;

public class FaceResult extends StrutsResultSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8711690218638618138L;
	
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		ResultHandler.instance().doResponce();
	}

}
