package com.zilu.util.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StringDataWriter extends DataWriter {

	BufferedWriter bw;
	
	boolean perLine = true;
	
	public boolean isPerLine() {
		return perLine;
	}

	public void setPerLine(boolean perLine) {
		this.perLine = perLine;
	}

	public StringDataWriter(String filename) throws IOException {
		File f = new File(filename);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		if (!f.exists()) {
			f.createNewFile();
		}
		bw = new BufferedWriter(new FileWriter(f));
	}
	
	@Override
	protected void doWrite(Object obj) throws IOException {
		bw.write(obj.toString());
		if (perLine) {
			bw.write("\r\n");
		}
	}

	@Override
	public void doClose() throws IOException {
		if (bw != null) {
			bw.close();
		}
	}

	@Override
	public void doflush() throws IOException {
		bw.flush();
	}

}
