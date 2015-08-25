package com.zilu.face.adapter;


public class AdapterFacade {
	
	private static AdapterFacade instance = new AdapterFacade();
	
	public static AdapterFacade instance() {
		return instance;
	}

	public MapAdapter beanUtilsAdapter() {
		return new BeanUtilsAdapter();
	}

	public StringAdapter jsonAdapter() {
		return new JSONAdapter();
	}
	
}
