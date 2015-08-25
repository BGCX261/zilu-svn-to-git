package com.zilu.face.adapter.filter;

import java.util.Map;


import com.zilu.face.adapter.MapAdapter;

public abstract class MapAdapterFilter implements MapAdapter {
	
	private MapAdapter mapAdapter;
	
	public MapAdapterFilter(MapAdapter mapAdapter) {
		this.mapAdapter = mapAdapter;
	}


	public void populate(Object bean, Map<String, Object> map) {
		beforePopulate(bean, map);
		mapAdapter.populate(bean, map);
		afterPopulate(bean, map);
	}

	public Map<String, Object> wrap(Object obj) {
		beforeWrap(obj);
		Map<String, Object> map = mapAdapter.wrap(obj);
		afterWrap(obj, map);
		return map;
	}
	
	protected abstract void afterPopulate(Object bean, Map<String, Object> map);

	protected abstract void beforePopulate(Object bean, Map<String, Object> map);

	protected abstract void afterWrap(Object obj, Map<String, Object> map);

	protected abstract  void beforeWrap(Object obj);
	
	
}
