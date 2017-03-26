package com.gdnyt.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.gdnyt.model.Column;
import com.gdnyt.model.Table;


public class ExcelService {

	
	
	public static List<Table> readExcel(String fileName) {
	
		Table table;
		List<Table> tables = null;
		boolean isE2007 = false; // 判断是否是excel2007格式
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName); // 建立输入流
			Workbook wb = new HSSFWorkbook(input);
			tables=new ArrayList<>();
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				table=new Table();
				Sheet sheet = wb.getSheetAt(i); // 获得第i个表单
				String sheetname = sheet.getSheetName();
				int st = sheetname.indexOf("(");
				int ed = sheetname.length();
				String comment = sheetname.substring(0, st);
				table.setComment(comment);
				String tablename = sheetname.substring(st + 1, ed - 1);
				table.setName(tablename);

				Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
				List<Column> columns=new ArrayList<>();
				rows.next();
				while (rows.hasNext()) {
					Row row = rows.next(); // 获得行数据
				//	System.out.println("Row #" + row.getRowNum()); // 获得行号从0开始
					Column column = new Column();
					column.setComment(row.getCell(1).getStringCellValue());
					column.setLength((int)row.getCell(2).getNumericCellValue());
				//	column.setIsnull(row.getCell(3).getStringCellValue().equals("否")?"NO":"YES");
					column.setType(row.getCell(5).getStringCellValue());
					column.setName(row.getCell(7).getStringCellValue());
					column.setColumnname(row.getCell(7).getStringCellValue());
					columns.add(column);
				}
				table.setColumnlist(columns);
				tables.add(table);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return tables;
	}
}
