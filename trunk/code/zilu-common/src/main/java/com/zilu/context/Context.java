package com.zilu.context;

public interface Context {

	public void add(String name, Object value);
	
	public Object get(String name);
	
	public void remove(String name);
}
