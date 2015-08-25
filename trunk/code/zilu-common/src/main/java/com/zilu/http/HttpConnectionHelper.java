package com.zilu.http;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.zilu.util.ClassUtils;
import com.zilu.util.Strings;
import com.zilu.util.file.IOFileUtil;

/**
 * 
 * @author chm
 * 通过jdk实现http连接的工具类
 *
 */
public class HttpConnectionHelper extends AbstractHttpHelper {
	
	private String requestEncode = "UTF-8";

	private String responseEncode = "UTF-8";
	
	private int connectTimeOut = DEFAULT_CONNECT_TIMEOUT;
	
	private int readTimeOut = DEFAULT_READ_TIMEOUT;

	static final String BOUNDARY = "---------------------------7da157cb0734"; 

	static final String LINE_SPLITOR = "\r\n";
	
	static final String CONTENT_DESC = "Content-Disposition: form-data; name=\"#fieldName\"";

	public InputStream getStream(String urlStr) throws IOException {
		URL xmcy = new URL(urlStr);
		try {
			HttpURLConnection httpCon = getHttpUrlConnection(xmcy);
			httpCon.connect();
			return httpCon.getInputStream();
		} catch(NullPointerException ne) {
			ne.printStackTrace();
			throw ne;
		}
	}

	public FaceResponce get(FaceRequest req) throws IOException {
		HttpURLConnection httpCon = null;
		try {
			URL xmcy = new URL(req.getRequestUrl());
			httpCon = getHttpUrlConnection(xmcy);
			packHeader(httpCon, req);
			httpCon.connect();
			
			FaceResponce resp = new FaceResponce();
			resp.respData = HttpUtil.getInputStreamContent(httpCon.getInputStream());	
			resp.encode = responseEncode;
			setHeader(httpCon, resp);
			return resp;
		} finally {
			releaseUrlConnection(httpCon);
		}
	}

	public FaceResponce post(FaceRequest req) throws IOException {
		HttpURLConnection httpCon = null;
		try {
			logger.info("post url:"+req.getRequestUrl());
			URL xmcy = new URL(req.getRequestUrl());
			httpCon = getHttpUrlConnection(xmcy);
			httpCon.setRequestMethod("POST");
			List<FileBean> files = req.getFiles();
			packHeader(httpCon, req);
			if (files == null|| files.size() == 0) {
				doPost(httpCon, req);
			}
			else {
				postWithFile(httpCon, req);
			}
			
			FaceResponce resp = new FaceResponce();
			resp.respData = HttpUtil.getInputStreamContent(httpCon.getInputStream());	
			resp.encode = responseEncode;
			setHeader(httpCon, resp);
			return resp;
		} finally {
		}
	}
	
	/**
	 * 不带文件的post提交
	 * @param httpCon
	 * @param req
	 * @throws IOException
	 */
	private void doPost(HttpURLConnection httpCon, FaceRequest req) throws IOException {
		Map<String, Object> parameters = req.getParameters();
		StringBuffer requestStr = new StringBuffer();
		for (Iterator<String> it = parameters.keySet().iterator(); it.hasNext();) {
			String name = (String) it.next();
			Object obj = parameters.get(name);
			if (obj == null) {
				continue;
			}
			if (ClassUtils.isPrimitiveArray(obj.getClass())) {
				Object[] objs = (Object[]) obj;
				for (Object o : objs) {
					String value = String.valueOf(o);
					value = Strings.isEmpty(value)? "" : value;
					requestStr.append(name).append("=").append(value).append("&");
				}
			}
			else if (obj instanceof Collection) {
				Collection c = (Collection) obj;
				Iterator cit = c.iterator();
				while (cit.hasNext()) {
					Object o = cit.next();
					String value = String.valueOf(o);
					value = Strings.isEmpty(value)? "" : value;
					requestStr.append(name).append("=").append(value).append("&");
				}
			}
			else {
				String value = String.valueOf(obj);
				requestStr.append(name).append("=").append(value).append("&");
			}
		}
		if (requestStr.length() > 1) {
			requestStr.deleteCharAt(requestStr.length() - 1);
		}
		httpCon.setRequestMethod("POST");
		OutputStream oStream = new DataOutputStream(httpCon.getOutputStream());
		oStream.write(requestStr.toString().getBytes(requestEncode));
		oStream.flush();
	}
	
	/**
	 * 带文件的post提交
	 * @param httpCon
	 * @param req
	 * @throws IOException
	 */
	private void postWithFile(HttpURLConnection httpCon, FaceRequest req) throws IOException {
		
//		设置类型和分隔符
		httpCon.setRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDARY); 
		httpCon.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
		httpCon.setRequestMethod("POST");
		OutputStream oStream = new DataOutputStream(httpCon.getOutputStream());
		Map<String, Object> parameters = req.getParameters();
		List<FileBean> files = req.getFiles();
		int contentLength = 0;
		
		String boundary = "--" + BOUNDARY;
		if (parameters != null&& parameters.size() > 0) {
//			模拟http post 写入参数
			for (Entry<String, Object> entry : parameters.entrySet()) {
				StringBuilder sb = new StringBuilder();
				sb.append(boundary).append(LINE_SPLITOR);
				String contentDesc = CONTENT_DESC.replace("#fieldName",entry.getKey());
				sb.append(contentDesc).append(LINE_SPLITOR);
				sb.append(LINE_SPLITOR);
				String value = (String) entry.getValue();
				sb.append(value).append(LINE_SPLITOR);
				byte[] data = sb.toString().getBytes(requestEncode);
				contentLength += data.length;
				IOFileUtil.writeContent(data, oStream);
			}
		}
		for (FileBean bean : files) {
//			模拟http oost 写入文件
			StringBuilder sb = new StringBuilder();
			sb.append(boundary).append(LINE_SPLITOR);
			sb.append(CONTENT_DESC.replace("#fieldName", bean.getFieldName()));
			sb.append(";filename=\"").append(bean.getFile().getName()).append("\"").append(LINE_SPLITOR);
			sb.append("Content-Type: ").append(ContentType.getContentTypeByName(bean.getFile().getName())).append(LINE_SPLITOR);
			sb.append(LINE_SPLITOR);
			byte[] data = sb.toString().getBytes(requestEncode);
			contentLength += data.length;
			IOFileUtil.writeContent(data, oStream);
			InputStream is = new FileInputStream(bean.getFile());
			contentLength += is.available();
			IOFileUtil.writeContent(is, oStream);
			is.close();
			oStream.write(LINE_SPLITOR.getBytes(requestEncode));
		}
		oStream.write((boundary+"--").getBytes(requestEncode));
		oStream.flush();
	}
	
	protected void packHeader(HttpURLConnection httpCon, FaceRequest req) {
		if (req.getHeaderMap() != null&& req.getHeaderMap().size() > 0) {
//			添加头部
			for (Entry<String, String> entry : req.getHeaderMap().entrySet()) {
				httpCon.addRequestProperty(entry.getKey(), entry.getValue());
			}
		}
	}
	
	protected void setHeader(HttpURLConnection httpCon, FaceResponce resp) {
		for (Entry<String, List<String>> entry : httpCon.getHeaderFields().entrySet()) {
			resp.headers.put(entry.getKey(), entry.getValue().get(0));
		}
	}
	
	protected HttpURLConnection getHttpUrlConnection(URL url)
			throws IOException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setReadTimeout(readTimeOut);
		con.setConnectTimeout(connectTimeOut);
		return con;
	}
	
	void releaseUrlConnection(HttpURLConnection httpCon) throws IOException {
		if (httpCon == null) {
			return ;
		}
		try {
			httpCon.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			httpCon.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			httpCon.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpCon = null;
	}
	
	public void setRequestEncode(String requestEncode) {
		this.requestEncode = requestEncode;
	}

	public void setResponseEncode(String responseEncode) {
		this.responseEncode = responseEncode;
	}

	public void setConnectTimeout(int milliSecond) {
		this.connectTimeOut = milliSecond;
		
	}

	public void setKeepAlive(boolean keepAlive) {
		throw new UnsupportedOperationException();
	}

	public void setMaxConnectionsPerHost(int maxHostConnections) {
		throw new UnsupportedOperationException();
	}

	public void setReadTimeout(int milliSecond) {
		this.readTimeOut = milliSecond;
		
	}

	public void setResponseEnocde(String encode) {
		this.responseEncode = encode;
	}
	

	public static void main(String args[]) throws IOException {
		String url = "http://www.facebook.com/home.php?#!/profile.php?ref=profile&id=1609175656";
		FaceRequest req = new FaceRequest();
		req.setRequestUrl(url);
		FaceResponce resp = new HttpConnectionHelper().get(req);
		System.out.println(resp.getPainString());
	}
}
