package com.zilu.http;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class MutipartFilter implements Filter  {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		boolean isMutipart = ServletFileUpload.isMultipartContent(req);
		if (isMutipart) {
//			生成带附件的请求
			req = new MutipartRequest(req);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		
	}

}
