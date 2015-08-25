package com.zilu.util.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DataReader {
	
	private List<ReaderListener> listeners = new ArrayList<ReaderListener>();
	
	public void addListener(ReaderListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ReaderListener listener) {
		listeners.remove(listener);
	}
	
	void fireListeners(int rowNum, Object[] columnValues) {
		for (ReaderListener listener : listeners) {
			listener.processReadLine(rowNum, columnValues);
		}
	}
	
	public abstract void doRead() throws IOException ;
}
