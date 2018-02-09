package service;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.ReportParams;
 
@Service
public class MeoServiceImp implements MeoService {
 
    @Autowired
    private ServletContext servletcontext;
 
    private MeoServiceImp(){
    	
    }
   
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void report_abortion(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	try{
    	Map mapReport = mapForJasper(dateReport, username);
    	
    	Connection conn = connectForJasper();
    	ResultSet rs = null;
    	PreparedStatement stmt = null;
    	
    	File sql_file = new File( servletcontext.getRealPath("/resources/sql/Abortion 2017 year.sql"));
        InputStream is = new FileInputStream(sql_file);
        String query = Util.importSQL(is).replace("'01.01.2017'", "'"+dateReport.getDateBegin()+"'").replace("'31.12.2017'", "'"+dateReport.getDateEnd()+"'");
        //System.out.println(query);
        stmt = conn.prepareStatement(query);
        rs = stmt.executeQuery();
        
        BigDecimal dou = new BigDecimal(0);
        BigDecimal abortion_on_mp_summ = new BigDecimal(0);
        
        while (rs.next()) {
        	System.out.println(rs.getString(1)+"_"+rs.getString(2)+"_count"+" -- "+rs.getString(3)+" -- "+rs.getString(4));
        	mapReport.put(rs.getString(1)+"_"+rs.getString(2)+"_count", rs.getInt(3));
        	mapReport.put(rs.getString(1)+"_"+rs.getString(2)+"_summ", rs.getString(4));
        	
        	if("Abortion_summ".contains(rs.getString(2)+"_summ") && !rs.getString(1).equals("O07")){ dou = dou.add(new BigDecimal(rs.getString(4))); }
        	// 20.04.2017 Добавлено условие && !rs.getString(1).equals("O07") (до снятия отчета)
        	if("Abortion_on_MP_summ".contains(rs.getString(2) + "_summ") && !rs.getString(1).equals("O07")){	abortion_on_mp_summ = abortion_on_mp_summ.add(new BigDecimal(rs.getString(4)));	}
        }
        
        mapReport.put("all_summ", dou);
        mapReport.put("all_on_mp_summ", abortion_on_mp_summ);
        stmt.close();
        rs.close();
        
        
        
        
		
		File f = new File( servletcontext.getRealPath("/resources/report/meo/report_meo_abortion.jrxml"));
		
		JasperReport jasperReport = JasperCompileManager.compileReport(f.getPath());
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport);
		
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(f.getPath().replace(".jrxml", ".xls")));
		exporter.exportReport();
		
        disconnectForJasper(conn);
		
		
    	}catch (Exception e) {
    		e.printStackTrace();
		}
   		
	}
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void report_ambulance(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	try{
    	Map mapReport = mapForJasper(dateReport, username);
    	
    	Connection conn = connectForJasper();
    	ResultSet rs = null;
    	PreparedStatement stmt = null;
    	
    	File sql_file = new File( servletcontext.getRealPath("/resources/sql/Ambulance.sql"));
        InputStream is = new FileInputStream(sql_file);
        
        //String dateBegin_edit = dateReport.getDateBegin().substring(6)+dateReport.getDateBegin().substring(3,5);
        //String dateEnd_edit = dateReport.getDateBegin().substring(6)+dateReport.getDateBegin().substring(3,5);
        String query = Util.importSQL(is).replace("201701", "'"+dateReport.getDateBegin()+"'").replace("201702", "'"+dateReport.getDateEnd()+"'");
        System.out.println("@@ "+ query);
        //System.out.println(query);
        stmt = conn.prepareStatement(query);
        rs = stmt.executeQuery();
        
        BigDecimal dou = new BigDecimal(0);
        BigDecimal abortion_on_mp_summ = new BigDecimal(0);
        
        while (rs.next()) {
        	System.out.println(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3));
        	
        	mapReport.put(rs.getString(1)+"_notdtp", rs.getString(2));
        	mapReport.put(rs.getString(1)+"_dtp", rs.getString(3));
        	
        	//if("Abortion_summ".contains(rs.getString(2)+"_summ") && !rs.getString(1).equals("O07")){ dou = dou.add(new BigDecimal(rs.getString(4))); }
        	//if("Abortion_on_MP_summ".contains(rs.getString(2) + "_summ")){	abortion_on_mp_summ = abortion_on_mp_summ.add(new BigDecimal(rs.getString(4)));	}
        }
        
        stmt.close();
        rs.close();
        
        
		
		File f = new File( servletcontext.getRealPath("/resources/report/meo/ambulance.jrxml"));
		
		JasperReport jasperReport = JasperCompileManager.compileReport(f.getPath());
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport);
		
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(f.getPath().replace(".jrxml", ".xls")));
		exporter.exportReport();
		
        disconnectForJasper(conn);
    	}catch (Exception e) {
    		e.printStackTrace();
		}
   		
	}
    

	private void disconnectForJasper(Connection conn) throws SQLException {
		conn.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map mapForJasper(ReportParams dateReport, String username) {
		Map mapReport = mapForJasper(dateReport);
	
		mapReport.put("username", username);
		return mapReport;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map mapForJasper(ReportParams dateReport) {
		Map mapReport = new HashMap();
		mapReport.put("dateBegin", dateReport.getDateBegin());
		mapReport.put("dateEnd", dateReport.getDateEnd());
		return mapReport;
	}

	private Connection connectForJasper() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		File f = new File( servletcontext.getRealPath("/WEB-INF/jdbc_collect.properties"));
		Properties properties = new Properties();
		
		try {
			
			properties.load(new FileInputStream(f.getPath()));
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Connect to "+properties.getProperty("jdbc.databaseurl") +" - "+properties.getProperty("jdbc.username")+"  -> is OK");	
	      	conn = DriverManager.getConnection(
  			properties.getProperty("jdbc.databaseurl"), 
  			properties.getProperty("jdbc.username"),
  			properties.getProperty("jdbc.password"));
	      	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	
	
    
}