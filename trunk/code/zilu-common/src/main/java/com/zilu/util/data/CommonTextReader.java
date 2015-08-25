package com.zilu.util.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.zilu.util.Strings;





public class CommonTextReader extends DataReader {

	public int startLine;
	
	private BufferedReader reader;
	
	private String fieldSplitor = ",";
	
	public CommonTextReader(InputStream is) {
		this.reader = new BufferedReader(new InputStreamReader(is));
	}
	
	public CommonTextReader(InputStream is, int startLine) {
		this.reader = new BufferedReader(new InputStreamReader(is));
		this.startLine = startLine;
	}
	
	public void doRead() throws IOException {
		String data = null;
		int line = 0;
		do {
			data = reader.readLine();
			if (line++ < startLine) {
				continue;
			}
			if (data == null) {
				break;
			}
			String flags[] =Strings.split(data, fieldSplitor);
			String values[] = new String[flags.length];
			for (int i = 0; i< flags.length; i++) {
				if (flags[i].startsWith("\"") && flags[i].equals("\"")) {
					values[i] = flags[i].substring(1, flags[i].length()-1);
				}
				values[i] = flags[i];
			}
			fireListeners(line - 1, values);
			
		} while (data != null) ;
	}
	
	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	public void setFieldSplitor(String fieldSplitor) {
		this.fieldSplitor = fieldSplitor;
	}
	

	
}
