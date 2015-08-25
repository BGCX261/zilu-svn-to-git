/**
 * 
 */
package com.zilu.util.file;


import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



/**
 * @author chm
 */
public class CommonFileUtil {
	
	

	public static List<File> findFiles(File root) {
		return  findFiles(root, FileSelectorFactory.getInstance().createDefaultSelector());
	}
	
	public static List<File> findFiles(File root,  FileSelector selector) {
		LinkedList<File> files = new LinkedList<File>();
		LinkedList<File> tmp = new LinkedList<File>();
		tmp.add(root);
		while(tmp.size() > 0) {
			File p = tmp.removeFirst();
			if (p.isDirectory()) {
				File[] lf = p.listFiles();
				for(int i = 0; i < lf.length; i++) {
					tmp.add(lf[i]);
				}
			}
			if (!selector.isAvailable(p)) {
				continue;
			}
			if (selector.getOrder() == FileConstants.TRAVEL_WIDTH) {
				files.addLast(p);
			}
			else if (selector.getOrder() == FileConstants.TRAVEL_HEIGHT) {
				files.addFirst(p);
			}
			
		}
		return files;
	}
	
	
	public static void copy(String source, String dest) throws IOException {
		File f = new File(source);
		File d = new File(dest);
		copy(f, d);
	}
	
	public static void copy(File source, File dest) throws IOException {
		if (!source.exists()) {
			throw new IOException("文件不存在");
		}
		int slength = source.getAbsolutePath().length() - source.getName().length() - 1;
		String dpath = dest.getAbsolutePath();
		List<File> ll = findFiles(source);
		for(int i = 0; i < ll.size(); i ++) {
			File f = ll.get(i);
			File nf = new File(dpath + f.getAbsolutePath().substring(slength));
			if (f.isDirectory()) {
				nf.mkdirs();
			}
			else {
				try {
					if (!nf.exists()) {
						nf.createNewFile();
					}
					IOFileUtil.copyContent(f, nf);
				} catch (IOException e) {
					e.printStackTrace();
					throw new IOException(source.getPath() + " 复制失败");	
				}
			}
		}
		System.out.println("复制成功");
	}
	
	
	public static void move(String source, String dest) throws IOException {
		File f = new File(source);
		File d = new File(dest);
		move(f, d);
	}
	
	public static void move(File source, File dest) throws IOException {
		if (!source.exists()) {
			throw new IOException("移动成功");
		}
		int slength = source.getAbsolutePath().length() - source.getName().length() - 1;
		String dpath = dest.getAbsolutePath();
		List<File> ll = findFiles(source);
		for(int i = 0; i < ll.size(); i ++) {
			File f = ll.get(i);
			File nf = new File(dpath + f.getAbsolutePath().substring(slength));
			if (f.isDirectory()) {
				nf.mkdirs();
			}
			else {
				 if(!f.renameTo(nf)) {
					 throw new IOException(source.getPath() + " 移动失败");	
				 }
			}
		}
		if (source.isDirectory()) {
			delete(source);
		}
	}
	public static void deleteFile(File f) throws IOException {
		if (!f.delete()) {
			throw new IOException(f.getPath() + " 删除失败");
		}
	}
	
	
	public static void delete(String path) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			throw new IOException("文件不存在");
		}
		delete(f);
	}
	
	public static void delete(File cf) throws IOException {
		if (cf.isFile()) {
			deleteFile(cf);
		}
		LinkedList<File> ll = (LinkedList<File>) findFiles(cf, 
				FileSelectorFactory.getInstance().createDefaultSelector(FileConstants.TRAVEL_HEIGHT));
		while (ll.size() > 0) {
			File ff = (File) ll.removeFirst();
			deleteFile(ff);
		}
	}
	
	
	public static void delete(String path, FileSelector selector) throws IOException {
		File f = new File (path);
		if (!f.exists()) {
			throw new IOException("文件不存在");
		}
		List<File> ll = findFiles(f, selector);
		for (File ff : ll) {
			delete(ff);
		}
	}
	
	
	public static long getLength(File f) {
		long length = 0;
		List<File> l = findFiles(f, FileSelectorFactory.getInstance().createTypeSelector(true));
		for (File ff : l) {
			length += ff.length();
		}
		return length;
	}
	
	
	public static String getShortFileName(String fileName) {
		String split = fileName.indexOf("\\") == -1? "/" : "\\";
		return fileName.substring(fileName.lastIndexOf(split) + 1);
	}
	
	public static String getFileType (String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	public static void main(String arg[]) throws IOException {
//		copy("E:\\Workspace\\forums", "E:\\Workspace\\1122\\");
//		move("E:\\Workspace\\1122\\", "E:\\Workspace\\uup");
//		delete("E:\\Workspace\\uup", ".svn");
//		delete("E:\\Workspace\\uup");
//		move("f:\\temp\\java_ee_sdk-5_02-windows-nojdk.exe", "f:\\files\\common");
//		System.out.println(getLength(new File("E:\\Workspace\\")));
		File f = new File("E:\\Microsoft Visual Studio 8\\2052");
		List<File> fs = findFiles(f);
		System.out.println(fs.size());
	}
}
