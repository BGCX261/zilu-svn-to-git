package com.zilu.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.zilu.util.BeanUtils;
import com.zilu.util.ClassUtils;
import com.zilu.util.ReflectUtil;


@SuppressWarnings("unchecked")
public class JSONPopulater  {

	public static final String ROOT_NAME = "root";
	
	Map<String, Class> aliasMap;
	
	String jsonStr;
	
	private String rootName;
	
	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	boolean isJSONArray() {
		return jsonStr.startsWith("[")&& jsonStr.endsWith("]");
	}
	
	boolean isJSONObject() {
		return jsonStr.startsWith("{")&& jsonStr.endsWith("}");
	}
	
	public JSONPopulater(String str) {
		jsonStr = str;
	}
	
	public void populate(Object obj)throws JSONException {
		if (isJSONArray()) {
			if (obj instanceof Collection) {
				Collection collection = (Collection) obj;
				collection.addAll(toList(rootName == null?ROOT_NAME: rootName, jsonStr));
			}
			else {
				throw new JSONException("incorrect populate Object type");
			}
		}
		else if (isJSONObject()) {
			if (obj instanceof Map) {
				Map objMap = toMap(jsonStr);

				((Map)obj).putAll(objMap);
			}
			else {
				popObj(obj);
			}
		}
		else {
		}
	}
	
	void popObj(Object obj) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonStr);
		Map map = jsonObject.toMap();
		Set<Entry> entrySet = map.entrySet();
		for (Entry entry : entrySet) {
			String key = (String)entry.getKey();
			Object value = entry.getValue();
			Class objClass = ReflectUtil.getFieldClass(obj.getClass(), key);
			if (objClass == null) {
				continue;
			}
			if (value instanceof String) {
				if (ClassUtils.isPrimitiveOrWrapper(objClass)
						|| objClass.getName().equals(String.class.getName())) {
					value = ReflectUtil.converPrimitiveOrString( objClass, String.valueOf(value));
					ReflectUtil.setPropertyValue(obj, key, value);
				}
				else {
					String str = String.valueOf(value);
					JSONPopulater populater = new JSONPopulater(str);
					populater.setAliasMap(aliasMap);
					populater.setRootName(key);
					Class clazz = null;
					if (aliasMap != null) {
						clazz = aliasMap.get(key);
					}
					clazz = (clazz == null||!ClassUtils.isAssignable(objClass, clazz))? objClass:clazz;
					Object valueObj = null;
					if (ClassUtils.isAssignable(Collection.class, clazz)) {
						if (ClassUtils.isAssignable(Set.class, clazz)) {
							valueObj = new HashSet();
						}
						else if (ClassUtils.isAssignable(List.class, clazz)) {
							valueObj = new ArrayList();
						}
					}
					else if (ClassUtils.isAssignable(Map.class, clazz)) {
						valueObj = new HashMap();
					}
					else {
						valueObj = ReflectUtil.instance(clazz);
					}
					
					if (valueObj != null) {
						populater.populate(valueObj);
						ReflectUtil.setPropertyValue(obj, key, valueObj);
					}
				}
			}
			else {
				throw new JSONException( "key:" + key + "is not allow type" + value.getClass());
			}
		}
	}
	
	Map toMap(String jsonStr) throws JSONException {
		Map objMap = new HashMap();
		JSONObject jsonObject = new JSONObject(jsonStr);
		Map map = jsonObject.toMap();
		Set<Entry> entrySet = map.entrySet();
		for (Entry entry : entrySet) {
			String key = (String)entry.getKey();
			Object value = entry.getValue();
			
			if (value instanceof String) {
				String str = String.valueOf(value);
				JSONPopulater populater = new JSONPopulater(str);
				populater.setAliasMap(aliasMap);
				populater.setRootName(key);
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
						populater.populate(valueObj);
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
					JSONPopulater populater = new JSONPopulater((String)o);
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

	public Map<String, Class> getAliasMap() {
		return aliasMap;
	}

	public void setAliasMap(Map<String, Class> aliasMap) {
		this.aliasMap = aliasMap;
	}
	
	public static void main(String args[]) {
		
	}
}
