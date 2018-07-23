package service;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import service.xml.Converter;
import util.Utilitys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import app.Appeal;
import dao.PetitDAO;
import domain.Callnight_markerday;
import domain.CauseL;
import domain.CdrQuery;
import domain.IRPLIST;
import domain.Petit;
import domain.Rectif1L;
import domain.Rectif2L;
import domain.Rectif3L;
import domain.Rectif4L;
import domain.ReportParams;
import domain.Subtype;
import domain.TypeL;
import domain.blOutboindLETTER2016;
 
@Service
public class PetitServiceImpl implements PetitService {
 
    @Autowired
    private PetitDAO petitDAO;
    @Autowired
    private ServletContext servletcontext;
    @Autowired
    private Utilitys utilitys;
    @Autowired
	Converter coverter;
 
    @Transactional
    public void addPetit(Petit petit) {
    	petitDAO.addPetit(petit);
    }
    
    @Transactional
    public void deleteSubType(Integer id) {
    	petitDAO.deleteByIdSubtype(id);
    }
    
    @Transactional
    public void updateDatePlan(String id, String date) {
    	petitDAO.update_PlaneDateField(id, date);
    }
 
    @Transactional
    public List<Petit> listPetit(String username,Set<String> role) {
        return petitDAO.listPetit(username,role);
    }
 
    @Transactional
    public void removePetit(Integer id) {
    	petitDAO.removePetit(id);
    }
    
    /*
     * Remove old records 
     */
    @Transactional
  
    public void removeOldmanyNotes(Integer id) {
    	petitDAO.removePetit(id);
    }
    
    private Map<Integer, TypeL> types = new LinkedHashMap<Integer, TypeL>();
    private Map<Integer, CauseL> causes = new LinkedHashMap<Integer, CauseL>();
    private Map<Integer, Rectif1L> rectifs1 = new LinkedHashMap<Integer, Rectif1L>();
    private Map<Integer, Rectif2L> rectifs2 = new LinkedHashMap<Integer, Rectif2L>();
    private Map<Integer, Rectif3L> rectifs3 = new LinkedHashMap<Integer, Rectif3L>();
    
	public PetitServiceImpl() {
		this.types = Appeal.fieldsDepends.types;
		this.causes = Appeal.fieldsDepends.causes;
		this.rectifs1 = Appeal.fieldsDepends.rectifs1;
		this.rectifs2 = Appeal.fieldsDepends.rectifs2;
		this.rectifs3 = Appeal.fieldsDepends.rectifs3;
	}
	
	public Map<Integer, TypeL> getTypes() {
		return types;
	}

	public Map<Integer, CauseL> getCauses() {
		return causes;
	}

	public Map<Integer, Rectif1L> getRectifs1() {
		return rectifs1;
	}

	public Map<Integer, Rectif2L> getRectifs2() {
		return rectifs2;
	}

	public Map<Integer, Rectif3L> getRectifs3() {
		return rectifs3;
	}

	@Override
	public Set<TypeL> findAllTypes() {
		Set<TypeL> set = new TreeSet<TypeL>();
		set.addAll(this.types.values());
		return set;
	}
	
	@Override
	public Set<CauseL> findCausesForTypes(int typeName) {
		Set<CauseL> nullSet = new HashSet<CauseL>();
		nullSet.add(new CauseL("-", 0));
		
		TypeL type = this.types.get(typeName);
		return type != null ? type.getCauses() : nullSet;
	}
	
	@Override
	public Set<Rectif1L> findRectifs1ForCauses(int causeName) {
		Set<Rectif1L> nullSet = new HashSet<Rectif1L>();
		nullSet.add(new Rectif1L("-", 0));
		
		CauseL cause = this.causes.get(causeName);

		return cause != null ? cause.getRectifs1() : nullSet;
	}
	
	@Override
	public Set<Rectif2L> findRectifs2ForRectifs1(int rectif1Name) {
		Set<Rectif2L> nullSet = new HashSet<Rectif2L>();
		nullSet.add(new Rectif2L("-", 0));
		
		Rectif1L rectif1 = this.rectifs1.get(rectif1Name);
		return rectif1 != null ? rectif1.getRectifs2() : nullSet;
	}
	
	@Override
	public Set<Rectif3L> findRectifs3ForRectifs2(int rectif2Name) {
		Set<Rectif3L> nullSet = new HashSet<Rectif3L>();
		nullSet.add(new Rectif3L("-", 0));
		
		Rectif2L rectif2 = this.rectifs2.get(rectif2Name);
		return rectif2 != null ? rectif2.getRectifs3() : nullSet;
	}
	
	@Override
	public Set<Rectif4L> findRectifs4ForRectifs3(int rectif3Name) {
		Set<Rectif4L> nullSet = new HashSet<Rectif4L>();
		nullSet.add(new Rectif4L("-", 0));
		
		Rectif3L rectif3 = this.rectifs3.get(rectif3Name);
		return rectif3 != null ? rectif3.getRectifs4() : nullSet;
	}
	
	private Petit petit = new Petit();
	
	@Override
	public void setSearchParams(Petit petit) {
		this.petit = petit;
	}
	
    @Transactional
    public List<Petit> listSearch(String username, String searchcheckinbound, String overdueappeal,Set<String> role) throws Throwable {
    	
    	List<Petit> lp = petitDAO.listSearch(petit, username,searchcheckinbound,overdueappeal,role);
    	
    	if (lp != null) {
    		return lp;
    	} else {
    		return new ArrayList<Petit>(Arrays.asList(petit));
    	}
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void pgForm(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport, username);
		
		JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\pg_form_1_1dop.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\pg_form_1_1.xls"));
		exporter.exportReport();
		
		jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\pg_form_1_2dop.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\pg_form_1_2.xls"));
		exporter.exportReport();
		
		jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\pg_form_2_3dop.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\pg_form_2_3.xls"));
		exporter.exportReport();
		
		jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\pg_form_2_1dop.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\pg_form_2_1.xls"));
		exporter.exportReport();
		
   		disconnectForJasper(conn);
	}
    
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void power_sp1_sp2(ReportParams dateReport, String username[]) throws SQLException, ClassNotFoundException, JRException {
    	
    	File f = new File( servletcontext.getRealPath("/resources/report/power_sp1_sp2.jrxml"));
    	
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport, username);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(f.getPath());
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(f.getPath().replace(".jrxml", ".xls")));
		exporter.exportReport();
		
   		disconnectForJasper(conn);
	}
    
    
    /* (non-Javadoc)
     * @see service.PetitService#report_strax3(domain.ReportParams, java.lang.String)
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void report_strax3(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	
    	File f = new File( servletcontext.getRealPath("/resources/report/report_strax3.jrxml"));
    	
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport, username);
		JasperReport jasperReport = JasperCompileManager.compileReport(f.getPath());
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(f.getPath().replace(".jrxml", ".xls")));
		exporter.exportReport();
		
   		disconnectForJasper(conn);
	}
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void report_drugs(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	
    	File f = new File( servletcontext.getRealPath("/resources/report/drugs.jrxml"));
    	
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport, username);
		JasperReport jasperReport = JasperCompileManager.compileReport(f.getPath());
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(f.getPath().replace(".jrxml", ".xls")));
		exporter.exportReport();
		
   		disconnectForJasper(conn);
	}
    
    
    
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void report_call(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport, username);
		
		JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\contact_call.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\contact_call.xls"));
		exporter.exportReport();
		
		jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\contact_call2.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\contact_call2.xls"));
		exporter.exportReport();
		
		
   		disconnectForJasper(conn);
	}
    
    /* (non-Javadoc)
     * @see service.PetitService#report_1(domain.ReportParams, java.lang.String)
     * отчет "Отчет по письменным обращениям граждан, поступившим в ТФОМС"
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void report_1(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	
    	File f = new File( servletcontext.getRealPath("/resources/report/report_letter_appeals.jrxml"));
    	
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport, username);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(f.getPath());
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(f.getPath().replace(".jrxml", ".xls")));
		exporter.exportReport();
		
		
   		disconnectForJasper(conn);
	}
    
    
    
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void report_overdue_appeal(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	
    	System.out.println("##############2 "+servletcontext.getRealPath("/")+"resources"+File.separator+"form_report"+File.separator);
    	/*Connection conn = connectForJasper();
		Map mapReport = mapForJasper(dateReport, username);
		JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\pg_form_1_1dop.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\pg_form_1_1.xls"));
		exporter.exportReport();
		
   		disconnectForJasper(conn);*/
	}
    
    

    @Transactional
	public Petit getPetit(Integer petitId) {
    	return petitDAO.getPetit(petitId);
	}
    
    @Transactional
	public void closeAppeal(Integer petitId) {
    	petitDAO.close(petitId);
	}
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void reportAppealPay(ReportParams dateReport, String username) throws SQLException, ClassNotFoundException, JRException {
    	
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport, username);
			
		JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\appeal_pay.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
	
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\appeal_pay.xls"));
		exporter.exportReport();
		
   		disconnectForJasper(conn);
	}

    // isDetectCellType()
    // xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void reportConsultOther(ReportParams dateReport) throws SQLException, ClassNotFoundException, JRException {
	
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport);
		
		JasperReport jasperReport;
		if(dateReport.isClinic()) jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\consult_other.jrxml");
		else jasperReport = JasperCompileManager.compileReport("C:\\Appeals3\\Appeal\\reports\\consult_other.jrxml");
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\consult_other.xls"));
		exporter.exportReport();
			
   		disconnectForJasper(conn);
	}
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void reportCountDetail(ReportParams dateReport) throws SQLException, ClassNotFoundException, JRException {
	
    	Connection conn = connectForJasper();
		Map<String, Object> mapReport = mapForJasper(dateReport);
		
		String report = "C:\\Appeals3\\Appeal\\reports\\count_detail.jrxml";
		if(dateReport.isClinic()) report = "C:\\Appeals3\\Appeal\\reports\\count_detail_polyclinic.jrxml";
		if(dateReport.isEachMedicalOrg()) report = "C:\\Appeals3\\Appeal\\reports\\count_detail_eachMedicalOrg.jrxml";
		if(dateReport.isClinic() && dateReport.isEachMedicalOrg()) report = "C:\\Appeals3\\Appeal\\reports\\count_detail_polyclinic_eachMedicalOrg.jrxml";
		
		JasperReport jasperReport = JasperCompileManager.compileReport(report);
		jasperReport.setProperty(JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapReport, conn);
		
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Appeals3\\Appeal\\reports\\count_detail.xls"));
		exporter.exportReport();
			
   		disconnectForJasper(conn);
	}

	private void disconnectForJasper(Connection conn) throws SQLException {
		conn.close();
	}

	
	private Map<String, Object> mapForJasper(ReportParams dateReport, String username) {
		Map<String, Object> mapReport = mapForJasper(dateReport);


		if(coverter.getMap().get("ROLE_TFOMS").toString().contains(username) ||
		   coverter.getMap().get("ROLE_ADMIN").toString().contains(username)){
			
			StringBuffer s = new StringBuffer();
			s.append(coverter.getMap().get("ROLE_TFOMS").toString());
			s.append(coverter.getMap().get("ROLE_ADMIN").toString());
			s.append(coverter.getMap().get("ROLE_SIMAZ").toString());
			s.append(coverter.getMap().get("ROLE_ER5001").toString());
			s.append(coverter.getMap().get("ROLE_ROSNO").toString());
			s.append(coverter.getMap().get("ROLE_ER5002").toString());
			s.append(coverter.getMap().get("ROLE_INGOS").toString());
			s.append(coverter.getMap().get("ROLE_ER5003").toString());
			username = s.toString();
			
			System.out.println("User names fo jasper report through mapForJasper (for log) \n"+ username);
		}
		
		mapReport.put("username", username);
		return mapReport;
	}
	
	
	
	private Map<String, Object> mapForJasper(ReportParams dateReport, String username[]) {
		Map<String, Object> mapReport = mapForJasper(dateReport);
		
		if(coverter.getMap().get("ROLE_TFOMS").toString().contains(username[0]) || coverter.getMap().get("ROLE_TFOMS").toString().contains(username[1]) ||
		   coverter.getMap().get("ROLE_ADMIN").toString().contains(username[0]) || coverter.getMap().get("ROLE_ADMIN").toString().contains(username[1])){
			
			mapReport.put("username_sp1", coverter.getMap().get("ROLE_SMO_SP1").toString());
			System.out.println("User names fo jasper report SP1 (for log) \n"+ coverter.getMap().get("ROLE_SMO_SP1").toString());
			
			mapReport.put("username_sp2", coverter.getMap().get("ROLE_SMO_SP2").toString());
			System.out.println("User names fo jasper report SP2 (for log) \n"+ coverter.getMap().get("ROLE_SMO_SP2").toString());
			
			mapReport.put("username_sp3", coverter.getMap().get("ROLE_SMO_SP3").toString());
			System.out.println("User names fo jasper report SP3 (for log) \n"+ coverter.getMap().get("ROLE_SMO_SP3").toString());

		}else{
			mapReport.put("username_sp1", username[0]);
			System.out.println("User names fo jasper report SP1 (for log) \n"+ username[0]);
			
			mapReport.put("username_sp2", username[1]);
			System.out.println("User names fo jasper report SP2 (for log) \n"+ username[1]);
			
			mapReport.put("username_sp3", username[2]);
			System.out.println("User names fo jasper report SP2 (for log) \n"+ username[2]);

		}
		
		return mapReport;
	}

	
	private Map<String, Object> mapForJasper(ReportParams dateReport) {
		Map<String, Object> mapReport = new HashMap<String, Object>();
		mapReport.put("dateBegin", dateReport.getDateBegin());
		mapReport.put("dateEnd", dateReport.getDateEnd());
		return mapReport;
	}

	private Connection connectForJasper() throws ClassNotFoundException,
			SQLException {
		Connection conn = null;
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("C:\\Appeals3\\Appeal\\WebContent\\WEB-INF\\jdbc2.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
		
		 // Class.forName(properties.getProperty("jdbc.driverClassName")).newInstance();
			Class.forName("oracle.jdbc.driver.OracleDriver");
	
		System.out.println("############################ "+properties.getProperty("jdbc.databaseurl") +" --------- "+properties.getProperty("jdbc.username"));
      	conn = DriverManager.getConnection(
      			properties.getProperty("jdbc.databaseurl"), 
      			properties.getProperty("jdbc.username"),
      			properties.getProperty("jdbc.password"));
		
		return conn;
	}

	@Override
	@Transactional
	public List<Date> getMaxDate() {

		return petitDAO.getMaxDate();
	}
	
	@Override
	@Transactional
	public void updateLastDate(Calendar cal) {
		
		cal.set(Calendar.HOUR, 0);
		Callnight_markerday cm = new Callnight_markerday();
		cm.setDateworked(cal.getTime());
		petitDAO.updateLastDate(cm);
	}

	@Override
	@Transactional
	public boolean isCeleb(Date date) throws ParseException {
		List<domain.Calendar> celebr = petitDAO.getCeleb(date);
		System.out.println("test "+ celebr);
		if (celebr.get(0).getWeekand().equals("1") || celebr.get(0).getCelebr().equals("1")) {return true;}
		else{ return false;}
		
	}
	
	
	/**
	 * Метод обновляет плановую дату ответа (после которой пойдет просрочка ответа) в зависимости от того какие поля заполнены 
	 * @param listPetit 
	 * @throws ParseException
	 */
	@Transactional
	public void createDate_plan(List<Petit> listPetit) throws ParseException{
		
		Calendar startdate = null;
		Calendar enddate = null;
		Calendar startdate_plus = null;
		DateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
		
		for(int i = 0; i < listPetit.size();i ++){
			if (listPetit.get(i).getBloutboindletter2016() == null) listPetit.get(i).setBloutboindletter2016(new blOutboindLETTER2016());
			if (listPetit.get(i).getBloutboindletter2016().getDate_between() == null) listPetit.get(i).getBloutboindletter2016().setDate_between("");
			listPetit.get(i).setDateInput(listPetit.get(i).getDateInput().substring(0, 11));
			
			if(utilitys.valid(listPetit.get(i)) == 1){
				utilitys.processDate(listPetit.get(i));
				startdate = utilitys.getCal();
				enddate = utilitys.getCal2();
				
				startdate_plus = utilitys.daysPlus((Calendar)startdate.clone(), 30,0);
				
				while(isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus((Calendar)startdate_plus.clone(), 1,0);
				};
				
				enddate.set(Calendar.HOUR_OF_DAY, 0);							
				enddate.set(Calendar.MINUTE, 0);
				enddate.set(Calendar.SECOND, 0);
				enddate.set(Calendar.MILLISECOND, 0);
				
				//System.out.println("@@@ "+ i + " -- "+ listPetit.get(i).getId()+" - "+startdate_plus.getTime()+" - "+df2.format(startdate_plus.getTime())+" - "+listPetit.get(i).getDateEnd());
				updateDatePlan(listPetit.get(i).getId().toString(), df2.format(startdate_plus.getTime()));
				
			}
			else if(utilitys.valid(listPetit.get(i)) == 2){
				utilitys.processDate(listPetit.get(i));
				startdate = utilitys.getCal();
				enddate = utilitys.getCal2();
				
				startdate_plus = utilitys.daysPlus((Calendar)startdate.clone(), 60,0);
				
				while(isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus((Calendar)startdate_plus.clone(), 1,0);
				};
				
				enddate.set(Calendar.HOUR_OF_DAY, 0);							
				enddate.set(Calendar.MINUTE, 0);
				enddate.set(Calendar.SECOND, 0);
				enddate.set(Calendar.MILLISECOND, 0);
				
				//System.out.println("@@@ "+ i + " -- "+ listPetit.get(i).getId()+" - "+startdate_plus.getTime()+" - "+df2.format(startdate_plus.getTime())+" - "+listPetit.get(i).getDateEnd());
				updateDatePlan(listPetit.get(i).getId().toString(), df2.format(startdate_plus.getTime()));
				
			}
			else if(utilitys.valid(listPetit.get(i)) == 3){
				utilitys.processDate(listPetit.get(i));
				startdate = utilitys.getCal();
				enddate = utilitys.getCal2();
	
				
				startdate_plus = utilitys.daysPlus((Calendar)startdate.clone(), 60,0);
				
				while(isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus((Calendar)startdate_plus.clone(), 1,0);
				};
				
				enddate.set(Calendar.HOUR_OF_DAY, 0);							
				enddate.set(Calendar.MINUTE, 0);
				enddate.set(Calendar.SECOND, 0);
				enddate.set(Calendar.MILLISECOND, 0);
				
				//System.out.println("@@@ "+ i + " -- "+ listPetit.get(i).getId()+" - "+startdate_plus.getTime()+" - "+df2.format(startdate_plus.getTime())+" - "+listPetit.get(i).getDateEnd());
				updateDatePlan(listPetit.get(i).getId().toString(), df2.format(startdate_plus.getTime()));

			}
			else if(utilitys.valid(listPetit.get(i)) == 4){
				utilitys.processDate(listPetit.get(i));
				startdate = utilitys.getCal();
				enddate = utilitys.getCal2();
	
				
				startdate_plus = utilitys.daysPlus((Calendar)startdate.clone(), 30,0);
				
				while(isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus((Calendar)startdate_plus.clone(), 1,0);
				};
				
				enddate.set(Calendar.HOUR_OF_DAY, 0);							
				enddate.set(Calendar.MINUTE, 0);
				enddate.set(Calendar.SECOND, 0);
				enddate.set(Calendar.MILLISECOND, 0);
				
				//System.out.println("@@@ "+ i + " -- "+ listPetit.get(i).getId()+" - "+startdate_plus.getTime()+" - "+df2.format(startdate_plus.getTime())+" - "+listPetit.get(i).getDateEnd());
				updateDatePlan(listPetit.get(i).getId().toString(), df2.format(startdate_plus.getTime()));
				
			}
		}
	}

	@Override
	public void createcdr(Map<String, String> map) throws UnsupportedEncodingException {
		
		Set s = map.entrySet();
	    Iterator it = s.iterator();
	    String mas[];
	    CdrQuery cdrquery;
	    
	    while ( it.hasNext() ) {
	    	
	       Map.Entry entry = (Map.Entry) it.next();
	       String key = (String) entry.getKey();
	       String value = (String) entry.getValue();
	       if(value.contains("88002221515") || value.contains("5001") || value.contains("5002") || value.contains("5003")){
	    	   
	    	   mas = new String(value.getBytes("CP1251"),"UTF-8").substring(1).replaceAll("\\?", "И").split(";");
	    	   cdrquery = new CdrQuery(mas[0], mas[1], mas[2], mas[3], mas[4], mas[5], mas[6], mas[7], mas[8], mas[9], mas[10], mas[11], mas[12], mas[13], mas[14], mas[15], mas[16], mas[17], mas[18], mas[19], mas[20], mas[21], mas[22]);
	    	   petitDAO.addCdrQuery(cdrquery);
	       }
	    }
		 
		
	}
	
	private Set<String> getRole() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = auth.getAuthorities().stream()
        .map(r -> r.getAuthority()).collect(Collectors.toSet());
        
        return roles;
	}

	@Override
	public List<Petit> parseArchiveFile(File file) throws IOException, JAXBException {
		
		List<Petit> ls = null ;
		 
		if(file.getName().contains(".zip")){
			
			List<File> xml_file = utilitys.extractArchive(file);
	        List<IRPLIST> model = utilitys.unmarshal(xml_file);
	        ls = utilitys.transformToEntity(model);
		
			
		}else if(file.getName().contains(".rar")){
			
		}

		

		return ls;
	}
	
    
}