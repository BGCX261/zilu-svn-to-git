package com.zilu.sql;

import java.util.Map;


public class SqlContext {
	
	Map<String, Object> params;
	
	String name;
	
	public SqlContext() {
	}
	
	public SqlContext(Map<String, Object> params) {
		this.params = params;
	}
	
	
	public SqlContext(String name, Map<String, Object> params) {
		this.name = name;
		this.params = params;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty(String name) {
		Object value = get(name);
		if (value instanceof String) {
			return (String) value;
		}
		else {
			return null;
		}
	}
	
	public Object get(String name) {
		Object value = null;
		if (params != null) {
			value = params.get(name);
		}
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(null instanceof String);
	}
}
