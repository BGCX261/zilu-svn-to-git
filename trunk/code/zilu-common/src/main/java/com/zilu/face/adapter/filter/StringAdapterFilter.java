package com.zilu.face.adapter.filter;

import com.zilu.face.adapter.StringAdapter;

public abstract class StringAdapterFilter implements StringAdapter {

	private StringAdapter stringAdapter;
	
	public StringAdapterFilter(StringAdapter stringAdapter) {
		this.stringAdapter = stringAdapter;
	}
	
	public void populate(String resultStr, Object obj) {
		resultStr = beforePopulate(resultStr, obj);
		stringAdapter.populate(resultStr, obj);
		afterPopulate(resultStr, obj);
	}

	public String wrap(Object obj) {
		String str = wrap(obj);
		str = afterWrap(str, obj);
		return str;
	}

	protected abstract void afterPopulate(String resultStr, Object obj);

	protected String beforePopulate(String resultStr, Object obj) {
//		默认不做修改
		return resultStr;
	}
	
	protected String afterWrap(String str, Object obj) {
//		默认不做修改
		return str;
	}
	
}
