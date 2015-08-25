package com.zilu.spring;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zilu.util.ReflectUtil;
import com.zilu.util.ResourceUtil;

public class SpringUtil {

	private static ApplicationContext ctx;
	
	public synchronized static void init(ServletContext servletContext) {
		ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}

	public static void mock() {
		String[] configPath = new String[] { getConfigFilePath() };
		ctx = new FileSystemXmlApplicationContext(configPath);
	}

	public synchronized static ApplicationContext getContext() {
		if (ctx == null) {
			mock();
		}
		return ctx;
	}

	public static Object getBean(String name) {
		return getContext().getBean(name);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return (T) getContext().getBean(name, clazz);
	}

	public static <T> T getBean(Class<T> clazz) {
		String[] names = getContext().getBeanNamesForType(clazz);
		return (T) getContext().getBean(names[0]);
	}

	public static String getConfigFilePath() {
		File f = ResourceUtil.getInWEB_INF("applicationContext.xml");
		return f == null ? "" : f.getAbsolutePath();
	}

	public static void main(String args[]) {
		mock();
	}
}
