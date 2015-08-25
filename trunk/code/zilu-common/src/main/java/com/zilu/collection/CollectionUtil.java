package com.zilu.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zilu.util.Strings;

public class CollectionUtil {

	
	public static void cleanNull(Map map) {
		List<Object> nullKeys = new ArrayList<Object>();
		for (Object key : map.keySet()) {
			Object obj = map.get(key);
			if (obj == null) {
				nullKeys.add(key);
			}
			else if ((obj instanceof String)&& (Strings.isEmpty((String)obj))) {
				nullKeys.add(key);
			}
			else if ((obj instanceof Number)&& (((Number)obj).intValue() == 0)) {
				nullKeys.add(key);
			}
		}
		for (Object key : nullKeys) {
			map.remove(key);
		}
	}
}
