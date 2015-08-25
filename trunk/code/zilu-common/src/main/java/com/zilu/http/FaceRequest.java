package com.zilu.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceRequest {
	
	String requestUrl;

	Map<String, String> headerMap = new HashMap<String, String>();
	
	Map<String, Object> parameters = new HashMap<String, Object>();
	
	List<FileBean> files;
	
	public FaceRequest(){
		
	}
	
	public FaceRequest(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public FaceRequest(String requestUrl, Map<String, Object> parameters) {
		this.requestUrl = requestUrl;
		this.parameters = parameters;
	}

	public FaceRequest(String requestUrl, Map<String, String> headerMap,
			Map<String, Object> parameters) {
		this.requestUrl = requestUrl;
		this.headerMap = headerMap;
		this.parameters = parameters;
	}

	public FaceRequest(String requestUrl, Map<String, String> headerMap,
			Map<String, Object> parameters, List<FileBean> files) {
		this.requestUrl = requestUrl;
		this.headerMap = headerMap;
		this.parameters = parameters;
		this.files = files;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public void addHeader(String name, String value) {
		headerMap.put(name, value);
	}
	
	public String getHeader(String name) {
		return headerMap.get(name);
	}
	
	public String removeHeader(String name) {
		return headerMap.remove(name);
	}
	
	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}
	
	public Object getParameter(String name) {
		return parameters.get(name);
	}
	
	public Object removeParameter(String name) {
		return parameters.remove(name);
	}
  
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public List<FileBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileBean> files) {
		this.files = files;
	}
}
