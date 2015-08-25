package com.zilu.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zilu.collection.CollectionUtil;




public class BeanUtils {

	
	public static Object clone(Object t) {
		if (isPrimitive(t.getClass())) {
			return t;
		}
		Class tClass = t.getClass();
		Object tt = ReflectUtil.instance(tClass);
		Field[] fields = tClass.getDeclaredFields();
		for (Field f : fields) {
			if (!ReflectUtil.isStatic(f)) {
				cloneField(tt, f, t);
			}
		}
		return tt;
	}
	
	/**
	 * 装配
	 * @param bean
	 * @param properties
	 */
	public static void populate(Object bean, Map<String, Object> properties) {
		try {
			CollectionUtil.cleanNull(properties);
			org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unchecked")
	public static void populateDeeply(Object bean, Map<String, Object> properties) {
		Iterator<Entry<String, Object>> it = properties.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Object> entry = it.next();
			Object obj = entry.getValue();
			if (obj instanceof Map) {
				try {
					it.remove();
					Field propField = ReflectUtil.getField(bean.getClass(), entry.getKey());
					Class<?> fieldType = propField.getType();
					Object propObj = ReflectUtil.instance(fieldType);
					populateDeeply(propObj, (Map<String, Object>)obj);
					ReflectUtil.setAndWrap(propField, bean, propObj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		populate(bean, properties);
	}
	
	public static void copy(Object dest, Object source) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 复制属性（去空）
	 * @param dest
	 * @param source
	 */
	public static void copySafty(Object dest, Object source) {
		Map<String, Object> map = describe(source);
		System.out.println(map);
		populate(dest, map);
	}
	
	public static Map<String, Object> describe(Object bean) {
		return describe(bean, true);
	}
	
	public static Map<String, Object> describe(Object bean, boolean cleanNull) {
		try {
			Map<String, Object> parameters = org.apache.commons.beanutils.BeanUtils.describe(bean);
			parameters.remove("class");
			if (cleanNull) {
				CollectionUtil.cleanNull(parameters);
			}
			return parameters;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	private static void cloneField(Object dest, Field field, Object source) {
		if (dest == null) { 
			return;
		}
		Object value = ReflectUtil.getAndWrap(field, source);
//		克隆原始对象 ，日期和字符串对象
		if (isPrimitive(field.getType())) {
			ReflectUtil.setAndWrap(field, dest, value);
		}
		else if (value instanceof Object[]) {
			Object[] objs = (Object[]) value;
			Object[] newObjs = new Object[objs.length];
			for (int i = 0; i < objs.length; i ++) {
				newObjs[i] = clone(objs[i]);
			}
			ReflectUtil.setAndWrap(field, dest, newObjs);
		}
//		克隆容器对象
		else if (value instanceof Collection) {
			Collection collection = (Collection) value;
			Collection newCollection = ReflectUtil.instance(collection.getClass());
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Object clone = clone(obj);
				newCollection.add(clone);
			}
			ReflectUtil.setAndWrap(field, dest, newCollection);
		}
//		克隆哈希容器对象
		else if (value instanceof Map) {
			Map map = (Map) value;
			Map newMap = ReflectUtil.instance(map.getClass());
			Iterator it = newMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				Object key = entry.getKey();
				Object v = entry.getValue();
				Object copyKey = clone(key);
				Object copyV = clone(v);
				newMap.put(copyKey, copyV);
			}
			ReflectUtil.setAndWrap(field, dest, newMap);
		}
//		克隆java对象
		else if (value instanceof Object){
			Object clone = clone(value);
//			处理在克隆中错误创建对象
			if (clone == null&& value != null) {
				return ;
			}
			ReflectUtil.setAndWrap(field, dest, clone);
		}
	}
	
	private static boolean isPrimitive (Class c) {
		return PrimitiveUtil.isPrimitiveOrWrapper(c)
			|| String.class == c
			|| Date.class.isAssignableFrom(c);
	}
	
	
}
