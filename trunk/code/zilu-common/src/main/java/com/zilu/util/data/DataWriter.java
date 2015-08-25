package com.zilu.util.data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class DataWriter {

	private int bufferedSize;
	
	List<Object> buffers ;
	
	public void write(List<? extends Object> objs) throws IOException {
		for(Object obj: objs) {
			write(obj);
		}
	}
	
	public void write(Object obj) throws IOException  {
		if (bufferedSize != 0) {
			add(obj);
			if (buffers.size() >= bufferedSize) {
				for (Object o : buffers) {
					doWrite(o);
				}
				buffers.clear();
			}
		}
		else {
			doWrite(obj);
			return;
		}
	}
	
	public void flush() throws IOException {
		if (buffers != null) {
			for (Object o : buffers) {
				doWrite(o);
			}
			buffers.clear();
		}
		doflush();
	}
	
	public abstract void doflush() throws IOException ;
	public void close() throws IOException {
		flush();
		doClose();
	}
	
	public abstract void doClose()  throws IOException ;

	private void add(Object obj) {
		if (buffers == null) {
			buffers = new LinkedList<Object>();
		}
		buffers.add(obj);
	}

	protected abstract void doWrite(Object obj) throws IOException ;
	
	public int getBufferedSize() {
		return bufferedSize;
	}

	public void setBufferedSize(int bufferedSize) {
		this.bufferedSize = bufferedSize;
	}
}
