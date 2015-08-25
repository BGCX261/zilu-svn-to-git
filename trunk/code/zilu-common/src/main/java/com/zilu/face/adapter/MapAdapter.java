package com.zilu.face.adapter;

import java.util.Map;


public interface MapAdapter {

	public void populate(Object bean, Map<String, Object> map);
	
	public Map<String, Object> wrap(Object obj);
	
}
