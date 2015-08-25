package com.zilu.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.zilu.spring.SpringUtil;
import com.zilu.sql.SqlFacade;
import com.zilu.util.ResourceUtil;

@Component
@Scope("singleton")
public class SystemLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger logger = Logger.getLogger(SystemLoader.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			System.out.println(event);
			SpringUtil.init(event.getApplicationContext());
			event.getApplicationContext();
//			加载查询
			loadQuery();
//			启动文件监听器
			FileMonitor.getInstance().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void loadQuery() throws Exception {
		List<File> files = ResourceUtil.getListInClassPath(ZiluGlobal.Config.QUERY_FILE_SUFFIX);
		for (File file : files) {
			logger.info("load query file name: " + file.getName());
			SqlFacade.get().load(new FileInputStream(file));
			FileMonitor.getInstance().addListener(file, SqlFacade.get());
		}
	}
}
