package com.zilu.face;

import java.util.HashMap;
import java.util.Map;

import com.zilu.http.HttpConnectionHelper;
import com.zilu.http.HttpHelper;


public class FaceClient {
	
	private String url;
	
	private HttpHelper httpHelper;
	
	private Map<String, BaseFace> faceMap = new HashMap<String, BaseFace>();
	
	public FaceClient(String url) {
		this.url = url;
		httpHelper = new HttpConnectionHelper();
	}

	public void addFace(BaseFace face) {
		faceMap.put(face.getClass().getName(), face);
	}
	
	public String getUrl() {
		return url;
	}

	public <T extends BaseFace> T getFace(Class<T> tclass) {
		BaseFace face = faceMap.get(tclass.getName());
		if (face == null) {
			return null;
		}
		face.httpHelper = this.httpHelper;
		face.baseUrl = this.url;
		return (T) face;
	}
	
}
