package com.zilu.face.adapter;

import java.util.Map;

public interface StringAdapter {
	
	public String wrap(Object obj);
	
	public void populate(String resultStr, Object obj);
	
	public void populate(String resultStr, Object obj, Map<String, Class> map);
}
