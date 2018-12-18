package com.firesoon.utils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

public class ExcelStyle {

	public static void fontStyle(Workbook workbook) {
		
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		//设置字体颜色
		font.setColor(HSSFColor.RED.index);

		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		int start = workbook.getSheetAt(0).getFirstRowNum();
		int end = workbook.getSheetAt(0).getLastRowNum();
		for (int i = start; i <= end; i++) {
			Row row = workbook.getSheetAt(0).getRow(i);
			int s = row.getFirstCellNum();
			int e = row.getLastCellNum();
			for (int j = s; j < e; j++) {
				Cell cell = row.getCell(j);
				//判断是否是数值类型 如果是数值且小于0则统一标红
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					if (cell != null && !"".equals(cell) && Double.parseDouble(cell.toString()) < 0) {
						cell.setCellStyle(style);
					}
				}

			}
		}
	}

	public static void bgColorStyle(Workbook workbook) {

		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		int start = workbook.getSheetAt(0).getFirstRowNum();
		int end = workbook.getSheetAt(0).getLastRowNum();
		//判断条件的索引位置
		int cell_data_index = -1;
		//需要加背景色cell的位置
		int need_color_index_bzbl = -1; //病种比例
		int need_color_index_jyk = -1; //净盈亏
		for (int i = start; i <= end; i++) {
			Row row = workbook.getSheetAt(0).getRow(i);
			// 用数据内容来设置格式
			// 取到需要判断条件的值
			if (cell_data_index != -1 && i != start) {
				Cell data_cell = row.getCell(cell_data_index);
				if ("1".equals(data_cell.getStringCellValue())) {
					row.getCell(need_color_index_bzbl).setCellStyle(style);
					row.getCell(need_color_index_jyk).setCellStyle(style);
				}
				row.removeCell(data_cell);
				// 移除需要移除的表头
				Row rowStart = workbook.getSheetAt(0).getRow(start);
				Cell start_cell = rowStart.getCell(cell_data_index);
				if(start_cell != null){
					rowStart.removeCell(start_cell);
				}
			}
			int s = row.getFirstCellNum();
			int e = row.getLastCellNum();
			for (int j = s; j < e; j++) {
				Cell cell = row.getCell(j);
				// 用数据内容来设置格式
				// 有个大前提 必须title只有一行
				if (i == start) {
					if ("是否预警".equals(cell.getStringCellValue())) {
						cell_data_index = cell.getColumnIndex();
					}
					if ("病种比例".equals(cell.getStringCellValue())) {
						need_color_index_bzbl = cell.getColumnIndex();
					}
					if ("净盈亏".equals(cell.getStringCellValue())) {
						need_color_index_jyk = cell.getColumnIndex();
					}
				}
			}
		}
	}

}
