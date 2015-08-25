package com.zilu.face.adapter.filter;

import java.util.Map;

import com.zilu.cipher.Cipher;
import com.zilu.cipher.CipherException;
import com.zilu.face.adapter.MapAdapter;
import com.zilu.util.Strings;

public class CipherMapAdapter extends MapAdapterFilter {

	private Cipher cipher;
	
	private String keyName;
	
	public CipherMapAdapter(String name,Cipher cipher, MapAdapter mapAdapter) {
		super(mapAdapter);
		this.cipher = cipher;
		this.keyName = name;
	}

	@Override
	protected void afterPopulate(Object bean, Map<String, Object> map) {
		
	}

	@Override
	protected void afterWrap(Object obj, Map<String, Object> map) {
		String params = Strings.mapToString(map, "&", "=");
		try {
			params = cipher.encrypt(params);
		} catch (CipherException e) {
			throw new RuntimeException(e);
		}
		map.clear();
		map.put(keyName, params);
	}

	@Override
	protected void beforePopulate(Object bean, Map<String, Object> map) {
		String params = (String) map.remove(keyName);
		if (params == null) {
			return;
		}
		try {
			params = cipher.decrypt(params);
		} catch (CipherException e) {
			throw new RuntimeException(e);
		}
		map.putAll(Strings.stringToMap(params, "&", "="));
	}

	@Override
	protected void beforeWrap(Object obj) {
		//do nothing
	}

}
