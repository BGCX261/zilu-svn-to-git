/**
 * 
 */
package com.zilu.util.file;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class IOFileUtil {

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getContent(final File file) throws IOException {
		if (!file.isFile()) {
			throw new IOException("");
		}
		final InputStream is = new FileInputStream(file);
		long size = file.length();
		byte[] data = new byte[(int) size];
		try {
			int read = 0;
			for (int pos = 0; pos < size && read >= 0; pos += read) {
				read = is.read(data, pos, (int) size - pos);
			}
		} finally {
			is.close();
		}
		return data;
	}
	
	public static byte[] getContent(final InputStream is) throws IOException {
		List<byte[]> byteList = new ArrayList<byte[]>();
		List<Integer> lengths = new ArrayList<Integer>();
		int length = 0;
		while (true) {
			byte[] buffer = new byte[1024];
			int al = is.read(buffer);
			if (al < 0) {
				break;
			}
			length = length + al;
			byteList.add(buffer);
			lengths.add(al);
		}
		byte[] result = new byte[length];
		int start = 0;
		for (int j= 0; j < byteList.size(); j++) {
			byte[] data = byteList.get(j);
			int size = lengths.get(j);
			for (int i = 0; i < size; i++) {
				result[start + i] = data[i];
			}
			start = start + size;
			if (start >= length - 1) {
				break;
			}
		}
		return result;
	}
	
	public static void writeContent(final File file, OutputStream os)
			throws IOException {
		if (!file.isFile()) {
			System.out.println(file.getAbsolutePath());
			throw new IOException("");
		}
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			writeContent(is, os);
		} finally {
			is.close();
		}
	}

	public static void writeContent(final InputStream is, OutputStream os)
			throws IOException {
		final byte[] buffer = new byte[1024];
		while (true) {
			int al = is.read(buffer);
			if (al < 0) {
				break;
			}
			os.write(buffer, 0, al);
		}
	}
	
	public static void writeContent(InputStream is, OutputStream os,
			int length) throws IOException {
		final byte[] buffer = new byte[1024];
		int left = length;
		while (true) {
			if (left <= 0) {
				break;
			}
			int al = 0;
			if (left < 1024) {
				al = is.read(buffer, 0, left);
			}
			else {
				al = is.read(buffer);
			}
			left = left - al;
			os.write(buffer, 0, al);
		}
		
	}

	public static void writeContent(final byte data[], OutputStream os)
			throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
		bos.write(data);
		bos.flush();
	}

	public static void copyContent(File source, File dest) throws IOException {
		if (!source.isFile() || !dest.isFile()) {
			throw new IOException("");
		}
		final OutputStream os = new FileOutputStream(dest);
		try {
			writeContent(source, os);
		} finally {
			os.close();
		}
	}
	

	public static InputStream StringToInputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}
	
	
	public static void main(String arg[]) {
		File f = new File("E:\\Workspace\\");
		System.out.println(f.length());
	}
}
