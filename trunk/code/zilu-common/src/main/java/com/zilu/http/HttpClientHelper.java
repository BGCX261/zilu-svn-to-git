package com.zilu.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartBase;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * 
 * @author chm
 * 基于httpClient实现的http 工具类
 *
 */
public class HttpClientHelper extends AbstractHttpHelper {
	
	boolean keepAlive;
	
	HttpClient client;
	
	private String requestEncode = "UTF-8";

	private String responseEncode = "UTF-8";
	
	public HttpClientHelper() {
		initHelper();
	}
	
	private void initHelper() {
		MultiThreadedHttpConnectionManager connectionManger = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setMaxTotalConnections(DEFAULT_MAX_TOTAL_CONNECTIONS);
        params.setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONS_PER_HOST);
        params.setConnectionTimeout(DEFAULT_CONNECT_TIMEOUT);
        params.setSoTimeout(DEFAULT_READ_TIMEOUT);
        params.setStaleCheckingEnabled(DEFAULT_STALE_CHECKING_ENABLED);
        connectionManger.setParams(params);
        client = new HttpClient(connectionManger);
	}
	
	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}
	
	public void setRequestEncode(String encode) {
		this.requestEncode = encode;
		client.getParams().setCredentialCharset(encode);
	}
	
	public void setResponseEncode(String encode) {
		this.responseEncode = encode;
		client.getParams().setContentCharset(encode);
	}
	
	public void setMaxConnectionsPerHost(int maxHostConnections) {
		client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(maxHostConnections);
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		client.getHttpConnectionManager().getParams().setMaxTotalConnections(maxTotalConnections);
	}

	public void setConnectTimeout(int milliSecond) {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(milliSecond);
	}

	public void setReadTimeout(int milliSecond) {
		client.getParams().setSoTimeout(milliSecond);
	}
	
	public FaceResponce get(FaceRequest req) throws IOException {
		GetMethod get = null;
		try {
			get = new GetMethod(req.getRequestUrl());
			
			if(keepAlive) {
				get.setRequestHeader("Connection", "Keep-Alive");
			}
			
			Map<String, String> headerMap = req.getHeaderMap();
			
			if (headerMap != null&& headerMap.size() > 0) {
//				添加头部
				for (Entry<String, String> entry : headerMap.entrySet()) {
					get.addRequestHeader(entry.getKey(), entry.getValue());
				}
			}
			
			client.executeMethod(get);
			FaceResponce faceResp = new FaceResponce();
			Header[] headers = get.getResponseHeaders();
			for (Header header : headers) {
				faceResp.headers.put(header.getName(), header.getValue());
			}
			
			faceResp.respData = HttpUtil.getInputStreamContent(get.getResponseBodyAsStream());
			faceResp.encode = get.getResponseCharSet();
			return faceResp;
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
	}
	

	public FaceResponce post(FaceRequest req) throws IOException {
		PostMethod post = null;
		String url = req.getRequestUrl();
		Map<String, Object> parameter = req.getParameters();
		List<FileBean> fileBeans = req.getFiles();
		Map<String, String> headerMap = req.getHeaderMap();
		try {
			post = new PostMethod(url);
			Set<Entry<String, Object>> entries = parameter.entrySet();
	        List<PartBase> paramsList = new ArrayList<PartBase>();
	        StringPart tempPart;
	        for(Iterator<Entry<String, Object>> it = entries.iterator(); it.hasNext(); paramsList.add(tempPart))
	        {
	        	Entry<String, Object> entry = it.next();
	            tempPart = new StringPart((String)entry.getKey(), String.valueOf(entry.getValue()), requestEncode);
	        }
	
	        RequestEntity rp = null;
	        
	        if (headerMap != null&& headerMap.size() > 0) {
//	        	添加头部
				for (Entry<String, String> entry : headerMap.entrySet()) {
					post.addRequestHeader(entry.getKey(), entry.getValue());
				}
			}
			
	        if(fileBeans != null&& fileBeans.size() > 0)
	        {
	        	for (FileBean bean : fileBeans) {
		            FilePart tempFilePart = new FilePart(bean.getFieldName(), bean.getFile(), ContentType.getContentTypeByName(bean.getFile().getName()), requestEncode);
		            paramsList.add(tempFilePart);
	        	}
	        	Part part[] = new Part[paramsList.size()];
	            for(int i = 0; i < paramsList.size(); i++) {
	                part[i] = (Part)paramsList.get(i);
	            }
	
	            rp = new MultipartRequestEntity(part, post.getParams());
	            java.io.OutputStream o = new ByteArrayOutputStream();
	            rp.writeRequest(o);
	        } else {
	            String buffer = HttpUtil.paramsToBuffer(parameter, post.getRequestCharSet());
	            rp = new StringRequestEntity(buffer, "application/x-www-form-urlencoded", null);
	        }
	        if(keepAlive) {
	            post.setRequestHeader("Connection", "Keep-Alive");
	        }
	        post.setRequestEntity(rp);
	        client.executeMethod(post);
	        
	        FaceResponce resp = new FaceResponce();
	        resp.respData = HttpUtil.getInputStreamContent(post.getResponseBodyAsStream());
	        
	        Header[] headers = post.getResponseHeaders();
	        
	        for (Header head : headers) {
	        	resp.headers.put(head.getName(), head.getValue());
	        }
	        
	        resp.encode = post.getResponseCharSet();
	        return resp;
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
	}
	
	
}
