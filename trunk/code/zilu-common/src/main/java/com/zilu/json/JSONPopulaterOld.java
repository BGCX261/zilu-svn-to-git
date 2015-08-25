package com.zilu.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.zilu.util.BeanUtils;
import com.zilu.util.ReflectUtil;

@SuppressWarnings("unchecked")
public class JSONPopulaterOld {
	
	public static final String ROOT_NAME = "root";
	
	Map<String, Class> aliasMap;
	
	String jsonStr;
	
	boolean isJSONArray() {
		return jsonStr.startsWith("[")&& jsonStr.endsWith("]");
	}
	
	boolean isJSONObject() {
		return jsonStr.startsWith("{")&& jsonStr.endsWith("}");
	}
	
	public JSONPopulaterOld(String str) {
		jsonStr = str;
	}
	
	public void populate(Object obj)throws JSONException {
		populate(ROOT_NAME, obj);
	}
	
	void populate(String name, Object obj)throws JSONException {
		if (isJSONArray()) {
			if (obj instanceof Collection) {
				Collection collection = (Collection) obj;
				collection.addAll(toList(name, jsonStr));
			}
			else {
				throw new JSONException("incorrect populate Object type");
			}
		}
		else if (isJSONObject()) {
			Map objMap = toMap(name, jsonStr);
			if (obj instanceof Map) {
				((Map)obj).putAll(objMap);
			}
			else {
				BeanUtils.populate(obj, objMap);
			}
		}
		else {
			obj = jsonStr;
		}
	}
	Map toMap(String name, String jsonStr) throws JSONException {
		Map objMap = new HashMap();
		JSONObject jsonObject = new JSONObject(jsonStr);
		Map map = jsonObject.toMap();
		Set<Entry> entrySet = map.entrySet();
		for (Entry entry : entrySet) {
			String key = (String)entry.getKey();
			Object value = entry.getValue();
			
			if (value instanceof String) {
				String str = String.valueOf(value);
				JSONPopulaterOld populater = new JSONPopulaterOld(str);
				populater.setAliasMap(aliasMap);
				Object valueObj = null;
				if (populater.isJSONArray()) {
					valueObj = new ArrayList();
				}
				else if (populater.isJSONObject()) {
					Class clazz = null;
					if (aliasMap != null) {
						clazz = aliasMap.get(key);
					}
					if (clazz != null) {
						valueObj = ReflectUtil.instance(clazz);
						if (valueObj == null) {
							throw new JSONException("error create class " + clazz);
						}
					}
				}
				if (valueObj != null) {
					try {
						populater.populate(key, valueObj);
						objMap.put(key, valueObj);
					} catch (Exception e) {
						objMap.put(key, value);
					}
				}
				else {
					objMap.put(key, value);
				}
			}
			else {
				objMap.put(key, value);
			}
		}
		return objMap;
	}
	
	List toList(String lstName, String jsonStr) throws JSONException {
		JSONArray array = new JSONArray(jsonStr);
		List list = array.toList();
		Class clazz = null;
		if (lstName != null&& aliasMap != null) {
			clazz = aliasMap.get(lstName);
		}
		List l;
		if (clazz != null) {
			l = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				if (o instanceof String) {
					Object obj = ReflectUtil.instance(clazz);
					if (obj == null) {
						throw new JSONException("error create class " + clazz);
					}
					JSONPopulaterOld populater = new JSONPopulaterOld((String)o);
					populater.setAliasMap(aliasMap);
					populater.populate(obj);
					l.add(obj);
				}
				else {
					throw new JSONException("error type : " + o.getClass() + " must be String in Collection");
				}
			}
		}
		else {
			l = list;
		}
		return l;
	}

	public void addAlias(String name, Class clazz) {
		if(aliasMap == null) {
			aliasMap = new HashMap<String, Class>();
		}
		aliasMap.put(name, clazz);
	}
	


	public Map<String, Class> getAliasMap() {
		return aliasMap;
	}

	public void setAliasMap(Map<String, Class> aliasMap) {
		this.aliasMap = aliasMap;
	}
	
}