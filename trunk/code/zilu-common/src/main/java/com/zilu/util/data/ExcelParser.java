package com.zilu.util.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelParser {

	public static final int CELL_TYPE_DATE = 11;
	
	public static final int CELL_TYPE_LONG = 12;
	
	public static HSSFWorkbook parse(InputStream is) throws IOException {
		 HSSFWorkbook workbook = new HSSFWorkbook(is);
		 return workbook;
	}
	
	public static Object getCellValue(HSSFCell cell) {
		return getCellValue(cell, null);
	}

	public static Object getCellValue(HSSFCell cell, Integer columnType) {
		if (columnType == null) {
			return getCellValue(cell, cell.getCellType());
		}
		else if (columnType ==HSSFCell.CELL_TYPE_STRING ) {
			return cell.getStringCellValue();
		}
		else if (columnType == HSSFCell.CELL_TYPE_NUMERIC) {
			return cell.getNumericCellValue();
		}
		else if (columnType == HSSFCell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		}
		else if (columnType == HSSFCell.CELL_TYPE_FORMULA) {
			return cell.getCellFormula();
		}
		else if (columnType == CELL_TYPE_DATE) {
			return cell.getDateCellValue();
		}
		else if (columnType == HSSFCell.CELL_TYPE_ERROR) {
			return cell.getErrorCellValue();
		}
		else if (columnType == HSSFCell.CELL_TYPE_BLANK) {
			return "";
		}
		else {
			System.out.println(columnType);
			return null;
		}
	}
	
	
	
}
