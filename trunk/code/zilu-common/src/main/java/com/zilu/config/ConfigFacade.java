package com.zilu.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;


import org.apache.log4j.Logger;

import com.zilu.util.file.CommonFileUtil;
import com.zilu.util.file.FileSelectorFactory;


/**
*
* @author chenhm
* @Company 福建邮科电信业务部
* @version 创建时间：Feb 24, 2009 10:49:33 AM
* @Describe 系统配置文件加载器
*
*/
public class ConfigFacade implements VariableStore, FileChangeListener {
	
	static ConfigFacade instance = new ConfigFacade();
	
	Map<String, String> properties = new HashMap<String, String>();
	
	private static final Logger logger = Logger.getLogger(ConfigFacade.class);
	
	private VariableExpander expander = new VariableExpander(this, "${", "}");
	
	public static ConfigFacade instance() {
		return instance;
	}
	
	/**
	 * 初始化
	 * @param appPath 应用环境物理地址
	 */
	public static void start(String configPath) {
		File folder = new File(configPath);
		instance.properties.put("configPath", configPath);
		List<File> files = CommonFileUtil.findFiles(folder, FileSelectorFactory.getInstance().createTypeNameSelector("properties"));
		
		for(File file : files) {
			instance.loadFile(file);
			FileMonitor.getInstance().addListener(file, instance);
		}
	}
	
	public void change(File f) {
		loadFile(f);
	}
	
	
	/**
	 * 初始化
	 */
	public void loadFile(File f) {
		try {
			Properties configs = new Properties();
			configs.load(new FileInputStream(f));
			for (Entry<Object, Object> entry : configs.entrySet()) {
				String utf8Value = (String) entry.getValue();
				utf8Value = new String(utf8Value.getBytes("ISO-8859-1"), "UTF-8");
				properties.put((String) entry.getKey(), utf8Value);
			}
				
			logger.info("load:" + configs);
			logger.info("加载配置文件成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("加载配置文件失败");
		}
	}
	
	/**
	 * 获取系统配置值
	 * @param name
	 * @return
	 */
	public static String getValue(String name) {
		return instance.getVariableValue(name);
	}
	
	/**
	 * 获取系统配置值
	 * @param name
	 * @return
	 */
	public static int getIntValue(String name) {
		return Integer.parseInt(instance.getVariableValue(name));
	}
	
	public String getVariableValue(String field) {
		String preExpansion = properties.get(field);
		if (preExpansion == null) {
			return null;
		}

		return expander.expandVariables(preExpansion);
	}

	public void flush() {
		String configPath = properties.get("configPath");
		File folder = new File(configPath);
		List<File> files = CommonFileUtil.findFiles(folder, FileSelectorFactory.getInstance().createTypeNameSelector("properties"));
		
		for(File file : files) {
			flush(file);
		}
	}
	
	public void flush(File f) {
		List<String> lines = new ArrayList<String>();
		BufferedInputStream fis;
		BufferedOutputStream bos;
		try {
			fis = new BufferedInputStream(new FileInputStream(f));
			byte[] data = new byte[fis.available()];
			fis.read(data);
			fis.close();
			String s = new String(data, "UTF-8");
			String[] dataFlags = s.split("\n|\r\n");
			for (String line : dataFlags) {
				String pair[] = line.split("=");
				if (pair.length >= 2) {
					String value = properties.get(pair[0].trim());
					if (value != null& !value.equals(pair[1].trim())) {
						line = pair[0].trim() + " = " + value.trim();
					}
				}
				lines.add(line);
			}
			fis.close();
			bos = new BufferedOutputStream(new FileOutputStream(f));
			for (String l : lines) {
				bos.write(l.getBytes("UTF-8"));
				bos.write("\r\n".getBytes());
			}
			bos.flush();
			bos.close();
			logger.info("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]) {
		System.out.println("WEB-INF".endsWith("properties"));
	}

	public Map<String, String> getProperties() {
		return properties;
	}
}

