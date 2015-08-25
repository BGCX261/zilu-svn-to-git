package com.zilu.http;

import java.io.File;

public class FileBean {
	
	String fieldName;
	
	File file;
	
	public FileBean(String fieldName, File file) {
		this.fieldName = fieldName;
		this.file = file;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


}
