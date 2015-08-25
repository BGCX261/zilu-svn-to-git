package com.zilu.face.adapter;

import java.util.Map;


import com.zilu.util.BeanUtils;


public class BeanUtilsAdapter implements MapAdapter {


	public Map<String, Object> wrap(Object obj) {
		return BeanUtils.describe(obj);
	}

	public void populate(Object bean, Map<String, Object> map) {
		BeanUtils.populate(bean, map);
	}
}
