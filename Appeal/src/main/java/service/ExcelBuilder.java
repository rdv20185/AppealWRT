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
import res.Fields;

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
		
		header.createCell(2).setCellValue("Дата закрытия");
		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("Источник");
		header.getCell(3).setCellStyle(style);
		
		header.createCell(4).setCellValue("Тип МО");
		header.getCell(4).setCellStyle(style);
		
		header.createCell(5).setCellValue("Связь");
		header.getCell(5).setCellStyle(style);
		
		header.createCell(6).setCellValue("Представление");
		header.getCell(6).setCellStyle(style);
		
		header.createCell(7).setCellValue("Номер письма");
		header.getCell(7).setCellStyle(style);
		
		header.createCell(8).setCellValue("МО");
		header.getCell(8).setCellStyle(style);
		
		header.createCell(9).setCellValue("Тип");
		header.getCell(9).setCellStyle(style);
		
		header.createCell(10).setCellValue("Фамилия");
		header.getCell(10).setCellStyle(style);
		
		header.createCell(11).setCellValue("Имя");
		header.getCell(11).setCellStyle(style);
		
		header.createCell(12).setCellValue("Территория");
		header.getCell(12).setCellStyle(style);
		
		header.createCell(13).setCellValue("Причина");
		header.getCell(13).setCellStyle(style);
		
		header.createCell(14).setCellValue("Уточнение");
		header.getCell(14).setCellStyle(style);
		
		header.createCell(15).setCellValue("Обоснованность");
		header.getCell(15).setCellStyle(style);
		
		header.createCell(16).setCellValue("Удовлетворенность");
		header.getCell(16).setCellStyle(style);
		
		header.createCell(17).setCellValue("Сумма компенсации");
		header.getCell(17).setCellStyle(style);
		
		header.createCell(18).setCellValue("Регистратор");
		header.getCell(18).setCellStyle(style);
		
		header.createCell(19).setCellValue("Исподнитель");
		header.getCell(19).setCellStyle(style);
		
		// -------------------------------------------
		
		header.createCell(20).setCellValue("От кого");
		header.getCell(20).setCellStyle(style);
		
		header.createCell(21).setCellValue("Номер письма");
		header.getCell(21).setCellStyle(style);
		
		header.createCell(22).setCellValue("Дата перенаправления для рассмотрения");
		header.getCell(22).setCellStyle(style);
		
		header.createCell(23).setCellValue("Адресат (кому направлено)");
		header.getCell(23).setCellStyle(style);
		
		header.createCell(24).setCellValue("Дата заакрытия расчетная");
		header.getCell(24).setCellStyle(style);
		
		header.createCell(25).setCellValue("Номер исходящий для ответа гражданину");
		header.getCell(25).setCellStyle(style);
		
		
		// create data rows
		int rowCount = 1;
		
		for (Petit petit : listpetits) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(petit.getId());
			aRow.createCell(1).setCellValue(petit.getDateInput());
			if(petit.getBlockger2016().getDate_end() != null) { aRow.createCell(2).setCellValue(petit.getBlockger2016().getDate_end());}
			else{ aRow.createCell(2).setCellValue(""); }
			aRow.createCell(3).setCellValue(petit.getSource().getSourceName());
			aRow.createCell(4).setCellValue(petit.getHsp().getHspName());
			aRow.createCell(5).setCellValue(petit.getConect().getConectName());
			aRow.createCell(6).setCellValue(petit.getPresent().getPresentName());
			aRow.createCell(7).setCellValue(petit.getLetterNum());
			aRow.createCell(8).setCellValue(petit.getMo().getMoName());
			aRow.createCell(9).setCellValue(petit.getType().getTypeName());
			aRow.createCell(10).setCellValue(petit.getSurname());
			aRow.createCell(11).setCellValue(petit.getName());
			aRow.createCell(12).setCellValue(petit.getTer().getTerId());
			aRow.createCell(13).setCellValue(petit.getCause().getCauseName());
			aRow.createCell(14).setCellValue(petit.getRectif1().getRectif1Name());
			aRow.createCell(15).setCellValue(petit.getValid().getValidName());
			aRow.createCell(16).setCellValue(petit.getSatisf());
			aRow.createCell(17).setCellValue(petit.getCompensSum());
			aRow.createCell(18).setCellValue(petit.getBlockger2016().getRegname());
			aRow.createCell(19).setCellValue(petit.getUsername());
			
			if(petit.getBlockger2016().getInbound_from() != null){
			Map<Integer,String> map = Fields.getInbound_from();
			aRow.createCell(20).setCellValue(map.get(Integer.valueOf(petit.getBlockger2016().getInbound_from())));}
			else{ aRow.createCell(20).setCellValue(""); }
			
			if(petit.getLetterNum() != null){
				aRow.createCell(21).setCellValue(petit.getLetterNum());}
				else{ aRow.createCell(21).setCellValue(""); }
			
			if(petit.getBloutboindletter2016() != null){
			if(petit.getBloutboindletter2016().getDate_redirect() != null){
				aRow.createCell(22).setCellValue(petit.getBloutboindletter2016().getDate_redirect());}
				else{ aRow.createCell(22).setCellValue(""); }
			}else{	aRow.createCell(22).setCellValue(""); }
			
			if(petit.getBloutboindletter2016() != null){
				if(petit.getBloutboindletter2016().getRedirect_adress() != null){
					Map<Integer,String> map = Fields.getInbound_from();
					aRow.createCell(23).setCellValue(map.get(Integer.valueOf(petit.getBloutboindletter2016().getRedirect_adress())));}
					else{ aRow.createCell(23).setCellValue(""); }
				}else{	aRow.createCell(23).setCellValue(""); }
			
			if(petit.getBlockger2016().getDate_plan_end() != null){
				aRow.createCell(24).setCellValue(petit.getBlockger2016().getDate_plan_end());
			}
			
			if(petit.getBloutboindletter2016() != null){
				if(petit.getBloutboindletter2016().getNumOutLetter() != null){
					aRow.createCell(25).setCellValue(petit.getBloutboindletter2016().getNumOutLetter());
			}
				}
		}
	}

}