package com.zilu.util.data;


public class SysOutReaderListener implements ReaderListener {

	public void processReadLine(int rowNum, Object[] columnValues) {
		StringBuilder sb = new StringBuilder();
		sb.append("row : ").append(rowNum);
		for (Object columnValue : columnValues) {
			sb.append("  ").append(columnValue);
		}
		System.out.println(sb.toString());
		
	}

}
