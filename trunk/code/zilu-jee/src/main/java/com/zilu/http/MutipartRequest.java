package com.zilu.http;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.zilu.config.ConfigFacade;

public class MutipartRequest extends HttpServletRequestWrapper {
	
	HttpServletRequest req;
	
	Map<String, String> parameters = new LinkedHashMap<String, String>();
	
	List<FileItem> fileItems = new ArrayList<FileItem>();
	
	static Logger logger = Logger.getLogger(MutipartRequest.class);

	public MutipartRequest(HttpServletRequest req) {
		super(req);
		this.req = req;
		printHeader();
		doParse();
	}

	private void printHeader() {
		Enumeration e = req.getHeaderNames();
		while (e.hasMoreElements()) {
			String h = (String) e.nextElement();
			System.out.println(h + "   " + req.getHeader(h));
		}
	}

	private void doParse() {
		String tmpPath = ConfigFacade.getValue("tmpFile.path");
		String encoding = ConfigFacade.getValue("http.encoding");
		
		File tmpDir = new File(tmpPath);
		boolean success = false;

		try {
			if (!tmpDir.exists()) {
				tmpDir.mkdirs();
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!success) {
			tmpPath = System.getProperty("java.io.tmpdir");
			tmpDir = new File(tmpPath);
		}
		
		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory(100 * 1024, tmpDir));
		upload.setHeaderEncoding(encoding);
		try {
			List<FileItem> items = upload.parseRequest(req);
			for (Iterator iter = items.iterator(); iter.hasNext();) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					fileItems.add(item);
				}
				else {
					parameters.put(item.getFieldName(), item.getString(encoding));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error parse request");
		}
	}


	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}


	@Override
	public Map getParameterMap() {
		return parameters;
	}

	public List<FileItem> getFiles() {
		return fileItems;
	}


}
