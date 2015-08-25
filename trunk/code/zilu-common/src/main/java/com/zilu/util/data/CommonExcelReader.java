package com.zilu.util.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



public class CommonExcelReader extends DataReader {

	
	private HSSFWorkbook workBook;
	
	private LinkedList<Integer> readColumns;
	
	private Map<Integer, Integer> readLineMap;

	private List<Integer> readSheets;
	
	private Map<Integer, Integer> columnFormats = new HashMap<Integer, Integer>();
	
	private int currentSheetIndex = 0;
	
	private int startLine;
	
	public static CommonExcelReader parse(InputStream is) throws IOException {
		return new CommonExcelReader(ExcelParser.parse(is));
	}
	
	public CommonExcelReader(InputStream is) throws IOException {
		this(ExcelParser.parse(is));
	}
	
	public CommonExcelReader (HSSFWorkbook workBook)  {
		this.workBook = workBook;
	}
	
	public CommonExcelReader (HSSFWorkbook workBook,  LinkedList<Integer> readColumns)  {
		this.workBook = workBook;
		this.readColumns = readColumns;
	}
	
	public void setStartLine(int line) {
		this.startLine = line;
	}
	
	public void addStartLine(int sheetNo, int line) {
		if (readLineMap == null) {
			readLineMap = new HashMap<Integer, Integer>();
			readSheets = new ArrayList<Integer>();
		}
		readSheets.add(sheetNo);
		readLineMap.put(sheetNo, line);
	}
	
	public void addColumnFormat(int column, int format) {
		columnFormats.put(column, format);
	}
	
	
	public void doRead() {
		while (hasNextSheet()) {
			HSSFSheet sheet = getNextSheet();
			int startLine = getStartLine();
			int currentLine = 0;
			Iterator it = sheet.rowIterator();
			while (it.hasNext()) {
				HSSFRow row = (HSSFRow) it.next();
				if (currentLine++ < startLine) {
					continue;
				}
				readLine(row);
			}
		}
	}
	
	public void readLine(HSSFRow row) {
		Object[] columnValues = new Object[readColumns == null? row.getPhysicalNumberOfCells() : readColumns.size()];
		if (readColumns != null) {
			for (int i = 0; i < readColumns.size(); i ++) {
				HSSFCell cell = row.getCell((int)readColumns.get(i));
				columnValues[i] = ExcelParser.getCellValue(cell);
			}
		}
		else {
			Iterator it = row.cellIterator();
			int i = 0;
			while (it.hasNext()) {
				HSSFCell cell = (HSSFCell) it.next();
				Integer columnType = columnFormats.get(i);
				columnValues[i++] = ExcelParser.getCellValue(cell, columnType);
			}
		}
		
		fireListeners(row.getRowNum(), columnValues);
	}
	
	
	
    boolean hasNextSheet() {
		if (readSheets != null) {
			return currentSheetIndex < readSheets.size();
		}
		else {
			return currentSheetIndex < workBook.getNumberOfSheets();
		}
		
	}
	
	HSSFSheet getNextSheet() {
		int realIndex = readSheets != null? readSheets.get(currentSheetIndex) : currentSheetIndex;
		currentSheetIndex ++;
		return workBook.getSheetAt(realIndex);
	}
	
	
	int getStartLine() {
		return readLineMap == null? startLine : readLineMap.get(currentSheetIndex);
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		HSSFWorkbook book = ExcelParser.parse(new FileInputStream("E:/xiamen_114_20090415.xls"));
		CommonExcelReader read = new CommonExcelReader(book);
		read.setStartLine(1);
		read.addListener(new SysOutReaderListener());
		read.addColumnFormat(0, ExcelParser.CELL_TYPE_DATE);
		read.doRead();
	}
	
}
