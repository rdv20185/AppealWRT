package service;

import java.sql.SQLException;

import domain.ReportParams;
import net.sf.jasperreports.engine.JRException;

public interface MeoService {
	
	/**
	 * Метод инициализирует выполнение отчета в JasperReport
	 * @param dateReport - метод содержит dateInput and DateEnd
	 * @param username  
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws JRException
	 */
	public void report_abortion(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException;

}
