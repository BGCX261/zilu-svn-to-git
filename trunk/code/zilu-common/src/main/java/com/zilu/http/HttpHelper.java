package com.zilu.http;

import java.io.IOException;

public interface HttpHelper {

	public FaceResponce get(FaceRequest req) throws IOException;
	
	public FaceResponce post(FaceRequest req) throws IOException ;
	
	public void setRequestEncode(String encode);
	
	public void setResponseEncode(String encode);
	
	public void setKeepAlive(boolean keepAlive);
	
	public void setMaxConnectionsPerHost(int maxHostConnections);
	
	public void setConnectTimeout(int milliSecond);
	
	public void setReadTimeout(int milliSecond);
	
}
