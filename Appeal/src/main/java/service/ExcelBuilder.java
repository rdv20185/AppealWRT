package service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import domain.Petit;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */

public class ExcelBuilder extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List<Petit> listpetits = (List<Petit>) model.get("listpetit");
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Поиск");
		sheet.setDefaultColumnWidth(30);
		
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		
		// create header row
		HSSFRow header = sheet.createRow(0);
		
		header.createCell(0).setCellValue("Номер");
		header.getCell(0).setCellStyle(style);
		
		header.createCell(1).setCellValue("Дата поступления");
		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("Источник");
		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("Тип МО");
		header.getCell(3).setCellStyle(style);
		
		header.createCell(4).setCellValue("Представление");
		header.getCell(4).setCellStyle(style);
		
		header.createCell(5).setCellValue("Номер письма");
		header.getCell(5).setCellStyle(style);
		
		header.createCell(6).setCellValue("МО");
		header.getCell(6).setCellStyle(style);
		
		header.createCell(7).setCellValue("Тип");
		header.getCell(7).setCellStyle(style);
		
		header.createCell(8).setCellValue("Фамилия");
		header.getCell(8).setCellStyle(style);
		
		header.createCell(9).setCellValue("Имя");
		header.getCell(9).setCellStyle(style);
		
		header.createCell(10).setCellValue("Территория");
		header.getCell(10).setCellStyle(style);
		
		header.createCell(11).setCellValue("Причина");
		header.getCell(11).setCellStyle(style);
		
		header.createCell(12).setCellValue("Уточнение");
		header.getCell(12).setCellStyle(style);
		
		header.createCell(13).setCellValue("Обоснованность");
		header.getCell(13).setCellStyle(style);
		
		header.createCell(14).setCellValue("Удовлетворенность");
		header.getCell(14).setCellStyle(style);
		
		header.createCell(15).setCellValue("Сумма компенсации");
		header.getCell(15).setCellStyle(style);
		
		header.createCell(16).setCellValue("Регистратор");
		header.getCell(16).setCellStyle(style);
		
		header.createCell(17).setCellValue("Исподнитель");
		header.getCell(17).setCellStyle(style);
		
		// create data rows
		int rowCount = 1;
		
		for (Petit petit : listpetits) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(petit.getId());
			aRow.createCell(1).setCellValue(petit.getDateInput());
			aRow.createCell(2).setCellValue(petit.getSource().getSourceName());
			aRow.createCell(3).setCellValue(petit.getHsp().getHspName());
			aRow.createCell(4).setCellValue(petit.getPresent().getPresentName());
			aRow.createCell(5).setCellValue(petit.getLetterNum());
			aRow.createCell(6).setCellValue(petit.getMo().getMoName());
			aRow.createCell(7).setCellValue(petit.getType().getTypeName());
			aRow.createCell(8).setCellValue(petit.getSurname());
			aRow.createCell(9).setCellValue(petit.getName());
			aRow.createCell(10).setCellValue(petit.getTer().getTerId());
			aRow.createCell(11).setCellValue(petit.getCause().getCauseName());
			aRow.createCell(12).setCellValue(petit.getRectif1().getRectif1Name());
			aRow.createCell(13).setCellValue(petit.getValid().getValidName());
			aRow.createCell(14).setCellValue(petit.getSatisf());
			aRow.createCell(15).setCellValue(petit.getCompensSum());
			aRow.createCell(16).setCellValue(petit.getBlockger2016().getRegname());
			aRow.createCell(17).setCellValue(petit.getUsername());
		}
	}

}