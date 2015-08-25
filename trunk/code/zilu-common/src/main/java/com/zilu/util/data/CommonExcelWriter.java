package com.zilu.util.data;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.zilu.util.BeanUtils;

public class CommonExcelWriter extends DataWriter {

	HSSFWorkbook workbook;
	OutputStream os;
	HSSFSheet sheet;
	DataDescription dataDecription;
	int currentRow = 0;
	
	public CommonExcelWriter(OutputStream os, DataDescription dataDescription) {
		 workbook = new HSSFWorkbook();
		 this.os = os;
		 this.sheet = workbook.createSheet();
		 this.dataDecription = dataDescription;
		 if (this.dataDecription == null) {
			 this.dataDecription = new DataDescription();
		 }
		 createHeader();
	}
	
	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	private void createHeader() {
		List<String> headerValues = dataDecription.getHeaderValues();
		if (headerValues != null) {
			HSSFRow row = sheet.createRow(currentRow);
			for (int i=0 ; i< headerValues.size(); i++) {
				HSSFCell cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(headerValues.get(i));
			}
			Iterator<Cell> it = row.cellIterator();
			int i = 0;
			while (it.hasNext()) {
				i++;
				it.next();
			}
			currentRow ++;
		}
	}
	
	@Override
	public void doClose() throws IOException {
		 workbook.write(os);
	}

	@Override
	protected void doWrite(Object obj) throws IOException {
		HSSFRow row = sheet.createRow(currentRow);
		List<Object> values = dataDecription.getColumnValues(obj);
		for (int i=0 ; i< values.size(); i++) {
			HSSFCell cell = row.createCell(i);
			Object value = values.get(i);
			if (value == null) {
				cell.setCellValue("");
			}else if (value instanceof Float) {
				cell.setCellValue((Float)value);
			}
			else if (value instanceof Double) {
				cell.setCellValue((Double)value);
			}
			else if (value instanceof Boolean) {
				cell.setCellValue((Boolean)value);
			}
			else if (value instanceof Date) {
				cell.setCellValue((Date)value);
			}
			else {
				cell.setCellValue(value.toString());
			}
		}
		currentRow ++;
	}

	@Override
	public void doflush() {
		//DO NOTHING
	}

}
