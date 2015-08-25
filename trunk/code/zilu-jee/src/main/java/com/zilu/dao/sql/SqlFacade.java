package com.zilu.dao.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.zilu.config.ConfigException;
import com.zilu.config.FileChangeListener;


public class SqlFacade implements FileChangeListener {
	
	private static final SqlFacade facade = new SqlFacade();
	
	Map<String, String> hqlMap = new HashMap<String, String>();
	
	public void load(InputStream is) {
		Properties prop = new Properties();
		try {
			prop.load(is);
		} catch(IOException e) {
			throw new ConfigException("error load query by " , e);
		}
		for (Entry<Object, Object> entry :prop.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			getHqlMap().put(key, value);
		}
	}
	
    Map<String, String> getHqlMap() {
		return hqlMap;
	}
    
    public static SqlFacade get() {
    	return facade;
    }
	
	public static String getSql(String name) {
		String sql = get().getHqlMap().get(name);
		if (sql == null) {
			throw new SqlException(" sql not found key: " + name);
		}
		return sql;

	}

	public void change(File f) {	
		try {
			load(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			throw new ConfigException(e);
		}
	}
}
