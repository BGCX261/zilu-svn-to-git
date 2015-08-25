package com.zilu.util;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;


import com.zilu.util.file.CommonFileUtil;
import com.zilu.util.file.FileSelectorFactory;

public class ResourceUtil {
	
	
	/**
	 * 获得工程中WEB-INF下的文件名为fileName 的文件
	 * @param fileName
	 * @return
	 */
	public static File getInWEB_INF(String fileName) {
		File f = new File(URLDecoder.decode(ResourceUtil.class.getClassLoader().getResource("").getFile()));
		List<File> springFiles = CommonFileUtil.findFiles(f.getParentFile(), FileSelectorFactory.getInstance().createNameSelector(fileName));
		if (springFiles.size() > 0) {
			return springFiles.get(0);
		}
		else {
			return null;
		}
	}
	
	public static List<File> getInWEB_INFType(String fileSuffix) {
		File f = new File(URLDecoder.decode(ResourceUtil.class.getClassLoader().getResource("").getFile()));
		List<File> hqlFiles = CommonFileUtil.findFiles(f.getParentFile(), FileSelectorFactory.getInstance().createTypeNameSelector(fileSuffix));
		return hqlFiles;
	}
	
	public static File getInClassPath(String fileName) {
		URL fileUri = ResourceUtil.class.getClassLoader().getResource(fileName);
		if (fileUri == null) {
			return null;
		}
		File f = new File(URLDecoder.decode(fileUri.getFile()));
		return f;
	}
	
	public static List<File> getListInClassPath(String fileSuffix) {
		File f = new File(URLDecoder.decode(ResourceUtil.class.getClassLoader().getResource("").getFile()));
		List<File> hqlFiles = CommonFileUtil.findFiles(f.getParentFile(), FileSelectorFactory.getInstance().createTypeNameSelector(fileSuffix));
		return hqlFiles;
	}
	
	public static void main(String args[]) {
		System.out.println(getInClassPath("grass.properties").isFile());
	}
	
}
