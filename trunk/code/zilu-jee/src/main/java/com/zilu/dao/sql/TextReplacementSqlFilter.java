package com.zilu.dao.sql;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;



public class TextReplacementSqlFilter extends DynamicSqlFilter {

	@Override
	public String doFilter(String sql, SqlContext context) {
		StringBuilder sb = new StringBuilder(sql);
		Iterator<Map.Entry<Integer,String>> properties = findTextReplacements(sb).entrySet().iterator();
		int valueLengths = 0;
		while (properties.hasNext()) {
			Map.Entry<Integer,String> entry = (Map.Entry<Integer,String>) properties.next();
			Integer position = (Integer) entry.getKey();
			String key = (String) entry.getValue();
			String value = context.getProperty(key);
			if (value == null) {
				String msg = "Text replacement '" + entry.getValue()
						+ "' has not been set.";
				throw new SqlException(msg);
			}
			sb.insert(position.intValue() + valueLengths, value);
			valueLengths += value.length();
		}
		return sb.toString();
	}
	
	private static SortedMap<Integer, String> findTextReplacements(StringBuilder statement) {
		TreeMap<Integer, String> textReplacements = new TreeMap<Integer, String>();
		int str = 0;
		while ((str = statement.indexOf("{{", str)) > -1) {
			int end = statement.indexOf("}}", str);
			String replacementKey = statement.substring(str + 2, end);
			statement.replace(str, end + 2, "");
			textReplacements.put(new Integer(str), replacementKey);
		}
		
		
		return textReplacements;
	}
}
