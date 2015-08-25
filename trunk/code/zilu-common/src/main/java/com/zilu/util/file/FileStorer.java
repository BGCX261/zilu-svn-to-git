package com.zilu.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class FileStorer {
	
	public static final String defaultKey = "default";
	
	public static final String tempFileKey = "temp";
	

	private Map<String, String> pathMap = new HashMap<String, String>();
	
	public void addPath(String key, String path) {
		pathMap.put(key, path);
	}
	
	/**
	 * 保存文件到临时目录
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public String saveTmpFile(File f) throws IOException {
		return saveFile(tempFileKey, f);
	}
	
	public String saveTmpFile(String fileName, InputStream is) throws IOException {
		return saveFile(tempFileKey, fileName, is);
	}
	
	/**
	 * 保存文件到默认路径
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public String saveFile(File f) throws IOException {
		return saveFile(defaultKey, f);
	}
	
	/**
	 * 保存文件
	 * @param key
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public String saveFile(String key, File f) throws IOException {
		String path = getPath(key);
		File file = new File(path + f.getName());
		checkFile(file);
		IOFileUtil.copyContent(f, file);
		return getStorePath(key, f.getName());
	}
	
	public String saveFile(String key, String path, File f) throws IOException {
		String tempPath = getPath(key);
		File file = new File(tempPath + path + f.getName());
		checkFile(file);
		IOFileUtil.copyContent(f, file);
		return getStoreKey(key) + path + f.getName();
	}
	
	/**
	 * 保存inputStream到默认路径
	 * @param filePath
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public String saveFile(String filePath, InputStream is) throws IOException {
		return saveFile(defaultKey, filePath, is);
	}
	
	/**
	 * 根据key保存输入流
	 * @param key
	 * @param filePath
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public String saveFile(String key, String filePath, InputStream is) throws IOException {
		String path = getPath(key);
		File f = new File(path + filePath);
		checkFile(f);
		FileOutputStream fos = new FileOutputStream(f);
		IOFileUtil.writeContent(is, fos);
		return getStorePath(key, filePath);
	}
	
	/**
	 * 根据存储的虚拟路径获得文件
	 * @param storePath
	 * @return
	 * @throws IOException 
	 */
	
	public File getFile(String storePath) throws IOException {
		return getFile(storePath, false);
	}
	public File getFile(String storePath, boolean create) throws IOException {
		int index = storePath.indexOf("/");
		if (index == -1) {
			index = storePath.indexOf("\\");
		}
		String key = storePath.substring(0, index);
		String path = getPath(key);
		String filePath = storePath.replaceFirst(key, path);
		File f = new File(filePath);
		if (create) {
			checkFile(f);
		}
		return f;
	}
	
	/**
	 * 根据key返回路径
	 * @param key
	 * @return
	 */
	private String getPath(String key) {
		String path = pathMap.get(key);
		if (path.endsWith("/")||path.endsWith("\\")) {
			return path;
		}
		else {
			return path + "/";
		}
	}
	
	private void checkFile(File file) throws IOException {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	private String getStoreKey(String key) {
		return key + "/";
	}
	
	public String getStorePath(String key, String path) {
		return getStoreKey(key) + path;
	}
	 
	public void setDefaultPath(String path) {
		addPath(defaultKey, path);
	}
	
	public void setTempPath(String path) {
		addPath(tempFileKey, path);
	}

}
