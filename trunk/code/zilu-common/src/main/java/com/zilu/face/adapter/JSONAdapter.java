package com.zilu.face.adapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zilu.json.JSONArray;
import com.zilu.json.JSONException;
import com.zilu.json.JSONObject;
import com.zilu.json.JSONPopulater;
import com.zilu.util.Strings;

public class JSONAdapter implements StringAdapter {

	public void populate(String resultStr, Object obj) {
		populate(resultStr, obj, null);
	}
	
	public void populate(String resultStr, Object obj, Map<String, Class> map) {
		try {
			if (Strings.isEmpty(resultStr)) {
				return;
			}
			JSONPopulater populater = new JSONPopulater(resultStr);
			populater.setAliasMap(map);
			populater.populate(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String,Object> populate(String resultStr) {
		Map<String,Object> objMap = new HashMap<String,Object>();
		try {
			if (Strings.isEmpty(resultStr)) {
				return null;
			}
			JSONPopulater populater = new JSONPopulater(resultStr);
			populater.populate(objMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return objMap;
	}

	public String wrap(Object obj) {
		Object jsonObj = null;
		if (obj instanceof Collection) {
			jsonObj = new JSONArray((Collection)obj);
		}
		else if (obj.getClass().isArray()){
			try {
				jsonObj = new JSONArray(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if (obj instanceof Map) {
			jsonObj = new JSONObject((Map)obj);
		}
		else {
			jsonObj = new JSONObject(obj);
		}
		return jsonObj.toString();
	}

	

}
