package web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Set;
 

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import res.Fields;
import res.TransferFiles;
import service.PetitListWrapper;
import service.PetitService;
import service.xml.Converter;
import util.Utilitys;
import domain.BlockGER2016;
import domain.Cause;
import domain.CauseL;
import domain.Conect;
import domain.Hsp;
import domain.Insur;
import domain.Mo;
import domain.Outboundmany;
import domain.Petit;
import domain.Present;
import domain.Rectif1;
import domain.Rectif1L;
import domain.Rectif2;
import domain.Rectif2L;
import domain.Rectif3;
import domain.Rectif3L;
import domain.Rectif4;
import domain.Rectif4L;
import domain.ReportParams;
import domain.Source;
import domain.Ter;
import domain.Type;
import domain.TypeL;
import domain.Subtype;

import ftp.FTPDownloadFileDemo;
import ftp.Option;
import exceptions.ValidationForRest;

/**
 * @author pylypiv.sergey
 *
 */
/**
 * @author pylypiv.sergey
 *
 */
@Controller
public class PetitController {
	
	@Autowired
    private PetitService petitService;
	@Autowired
    private Utilitys utilitys;
	@Autowired
    private ServletContext servletcontext;
    @Autowired
	Converter coverter;
	
    public PetitController() {
    	
    }
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="ValidException occured")
    @ExceptionHandler(ValidationForRest.class)
	public void handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){
    	ex.printStackTrace();
	}	
   
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody List<Petit> addPetit(Petit petit,String submitted,HttpServletRequest request,ModelMap model, Authentication aut) throws ParseException, InterruptedException, IOException, ValidationForRest {

    	petit.setSurname(petit.getSurname().toUpperCase());
    	petit.setName(petit.getName().toUpperCase());
    	petit.setPatrony(petit.getPatrony().toUpperCase());
    	
    	String para = submitted;
		validRest(para,petit);
	    	
	    	//$('#moId').val() != 0 && ($('#hspId').val() == 0 || $('#typempid').val() == 0)
	    	
	    	
	    	if(para.trim().equals("Завершить")){
	    		if(petit.getPresentId() == 2 && petit.getBloutboindletter2016().getDate_response().equals("")){
	    			petit.getBlockger2016().setState(2);
	    			if(petit.getBloutboindletter2016().getResponsible().equals("")){ petit.setUsername(getUserName());}
	         		else{petit.setUsername(petit.getBloutboindletter2016().getResponsible());}
	    		}else{
	    			
	    			if(petit.getPresentId() == 2 && !petit.getBloutboindletter2016().getDate_response().equals(""))
	    			{
	    				petit.getBlockger2016().setState(3);
	    				
	    	  		  	DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
		        		petit.getBlockger2016().setDate_end(df.parse(petit.getBloutboindletter2016().getDate_response().concat(" 01:00:00.123")));
		        		
		        		if(petit.getBloutboindletter2016().getResponsible().equals("")){ petit.setUsername(getUserName());}
		         		else{petit.setUsername(petit.getBloutboindletter2016().getResponsible());}
	    			}
	    			else{
			    			petit.getBlockger2016().setState(3);
			        		petit.getBlockger2016().setDate_end(new Date());
	    			}
	    		}
	    	}
	    	
			return adds(petit,request,submitted,model);
    	
    }
    
    private @ResponseBody List<Petit> adds(Petit petit,HttpServletRequest request,String submitted,ModelMap model) throws UnsupportedEncodingException, ParseException {
    	
    	
    	petit = createDatePlan(petit);
    	
		/*
		 * Ловим с клиента в переменную ff поле date_end
		 */
		String ff = request.getParameter("fil");
		if(ff !=null && !ff.equals(""))
		{
    		
  		  	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    		petit.getBlockger2016().setDate_end(df.parse(ff));
		}
		String para =submitted;//new String(submitted.getBytes("ISO-8859-1"),"UTF-8");
		
		/* Если нажата кнопка сохранить то в поле username добавляется ключ (ключ приходит с клиента input select - "назначить")
		 * Ключ - это значение при котором записи из базы будут доступны определенным группам пользователей
		 */
		/*
		 * Обрабатывается нажатие клавиши назначить в режиме редактирования ночным 
		 */
	   	
		
		if(para.trim().equals("Сохранить"))
		{
		}else
		{ 
			if(para.trim().equals("Назначить"))
			{
	    		petit.getBlockger2016().setState(1);
	    		petit.getBlockger2016().setRegname(getUserName());
	    	}else
	    	{
	    		if(petit.getPresentId() == 2 && para.trim().equals("Изменить") && petit.getBlockger2016().getState() == 3 ){
	    			
	    			DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
	        		try { petit.getBlockger2016().setDate_end(df.parse(petit.getBloutboindletter2016().getDate_response().concat(" 00:00:00.001")));} catch (ParseException e) {
						e.printStackTrace();
					}
	        		if(petit.getBloutboindletter2016().getResponsible().equals("")){ petit.setUsername(getUserName());}
 	        		else{petit.setUsername(petit.getBloutboindletter2016().getResponsible());}
	    		}
	    		else{
	    			
	    			if(petit.getPresentId() == 2 && para.trim().equals("Изменить") && petit.getBlockger2016().getState() == 2 ){
 	    				petit.setUsername(getUserName());
 		    		}
	    			else{
		    			if(petit.getPresentId() != 2 && para.trim().equals("Изменить") && petit.getBlockger2016().getState() == 1 ){
		    				
		    				petit.getBlockger2016().setState(2);
			        		petit.setUsername(getUserName());
			    		}
	    			
	    			if(petit.getPresentId() != 2)
	    			petit.setUsername(getUserName());
	    			}
	    		}
	    	}	
		}

		if(petit.getPresentId() == 2){
			petit.getBloutboindletter2016().getMany().get(0).setBloutboindletter2016(petit.getBloutboindletter2016());
			petit.getBloutboindletter2016().getMany().get(1).setBloutboindletter2016(petit.getBloutboindletter2016());
			petit.getBloutboindletter2016().getMany().get(2).setBloutboindletter2016(petit.getBloutboindletter2016());
			petit.getBloutboindletter2016().setPetit(petit);
			
		}else{
			petit.setBloutboindletter2016(null);
		}
		
		// Add entity to subtype
		
		if(petit.getSubtype() != null){
			
			for(int i=0; i < petit.getSubtype().size(); i++){
				Subtype sb = petit.getSubtype().get(i);
				if(sb.getSubcause() != null || sb.getSubrectif() != null){
					petit.getSubtype().get(i).setPetit(petit);
				}
				
			}
			
		}
		
		
	    petit.getBlockger2016().setPetit(petit);
	    
		petitService.addPetit(petit);
		List<Petit> pl = new ArrayList<Petit>();//petitService.listPetit(getUserName());
		pl.add(petit);
		/*for(Petit pt : pl)
    	{
    		pt.setDateInput(pt.getDateInput().substring(8, 10) + "." + pt.getDateInput().substring(5, 7) + "." + pt.getDateInput().substring(0, 4));
    	}
    	*/
	    ModelAndView modelAndView = new ModelAndView();
	    model.addAttribute("petitList", pl);
        modelAndView.addObject("petitList", pl);
	    
		return pl;
	}
    
   
    
   @RequestMapping(value = "/nightcallfile/{petitId}", method = RequestMethod.GET)
   public void downloadcall(@PathVariable("petitId")Integer petitId,HttpServletRequest request,HttpServletResponse response) throws IOException
   {
    	Petit petit = petitService.getPetit(petitId);
    	
		ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);
 
        //String fullPath = appPath + filePath ;      
        String fullPath = petit.getBlockger2016().getFilecall();
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
 
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
	    	
	    	
	}
    
   private String getUserName() {
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String name = user.getUsername();
		return name;
	}
   
   private Set<String> getRole() {
		
   	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       Set<String> roles = auth.getAuthorities().stream()
       .map(r -> r.getAuthority()).collect(Collectors.toSet());
       
       return roles;
	}
	
   @RequestMapping(value = "/types", method = RequestMethod.GET)
	public @ResponseBody
	Set<TypeL> findAllTypes() {
		return this.petitService.findAllTypes();
	}
	
   @RequestMapping(value = "/causes", method = RequestMethod.GET)
	public @ResponseBody
	Set<CauseL> causesForTypes(
			@RequestParam(value = "typeName", required = true) int type) {
		return this.petitService.findCausesForTypes(type);
	}
	
   @RequestMapping(value = "/rectifs1", method = RequestMethod.GET)
	public @ResponseBody
	Set<Rectif1L> rectifs1ForCauses(
			@RequestParam(value = "causeName", required = true) int cause) {
		return this.petitService.findRectifs1ForCauses(cause);
	}
	
	@RequestMapping(value = "/rectifs2", method = RequestMethod.GET)
	public @ResponseBody
	Set<Rectif2L> rectifs2ForRectifs1(
			@RequestParam(value = "rectif1Name", required = true) int rectif1) {
		return this.petitService.findRectifs2ForRectifs1(rectif1);
	}
	
	@RequestMapping(value = "/rectifs3", method = RequestMethod.GET)
	public @ResponseBody
	Set<Rectif3L> rectifs3ForRectifs2(
			@RequestParam(value = "rectif2Name", required = true) int rectif2) {
		return this.petitService.findRectifs3ForRectifs2(rectif2);
	}
	
	@RequestMapping(value = "/rectifs4", method = RequestMethod.GET)
	public @ResponseBody
	Set<Rectif4L> rectifs4ForRectifs3(
			@RequestParam(value = "rectif3Name", required = true) int rectif3) {
		return this.petitService.findRectifs4ForRectifs3(rectif3);
	}
    
    
	
 
    
    @RequestMapping("/reporting")
    public String reporting(ModelMap map,HttpServletRequest request) {
    	map.put("dateReport", new ReportParams());
        return "reporting";
    }
    
    

	
	@RequestMapping(value = "/report.html", method = RequestMethod.POST)
    public String report(@ModelAttribute("dateReport") @Valid ReportParams dateReport,@RequestParam(value = "insurcomp",required=false) String insursmo, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
		
		if(insursmo == null){
			
			if(getRole().contains("ROLE_ROSNO")){
				String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.pgForm(dateReport,users);
			}else
			
			if(getRole().contains("ROLE_INGOS")){
				String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.pgForm(dateReport,users);
			}else
			
			if(getRole().contains("ROLE_SIMAZ")){
				String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.pgForm(dateReport,users);
			}
			
			else {petitService.pgForm(dateReport, getUserName());}
		}
		else{
			
			if(insursmo.equals("smo_simaz")){
				String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.pgForm(dateReport,users);
			}
			
			if(insursmo.equals("smo_rosno")){
				String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.pgForm(dateReport,users);
			}
			
			if(insursmo.contains("smo_ingos")){
				String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.pgForm(dateReport,users);
			}
		}	
    	return "reporting";
	}
	
	/**
	 * Отчет нагрузки СП1 СП2. Дата начала отчета должна быть не ранее 16.06.2017 (с этой даты начали отлеживать нагрузку,
	 * поменяли логины и добавили роли для персонификации 1-го 2-го уровней.
	 * @param dateReport - дата начала отчета (не ранее 16.06.2017)
	 * @param insursmo  - флаг СМО
	 * @param bindingResult
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JRException
	 */
	@RequestMapping(value = "/report_power_sp.html", method = RequestMethod.POST)
    public String report_power_sp(@ModelAttribute("dateReport") @Valid ReportParams dateReport,@RequestParam(value = "insurcomp",required=false) String insursmo, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
		
		if(insursmo == null){
			
			if(getRole().contains("ROLE_ROSNO")){
				String []users = {coverter.getMap().get("ROLE_ROSNO_SP1").toString(),coverter.getMap().get("ROLE_ROSNO_SP2").toString()};
				System.out.println("Users name " + getClass().getName()+ " for log \n"+ Arrays.toString(users) );
				petitService.power_sp1_sp2(dateReport,users);
			}else
			
			if(getRole().contains("ROLE_INGOS")){
				String[] users = {coverter.getMap().get("ROLE_INGOS_SP1").toString(),coverter.getMap().get("ROLE_INGOS_SP2").toString()};
				System.out.println("Users name " + getClass().getName()+ " for log \n"+Arrays.toString(users) );
				petitService.power_sp1_sp2(dateReport,users);
			}else
			
			if(getRole().contains("ROLE_SIMAZ")){
				String[] users = {coverter.getMap().get("ROLE_SIMAZ_SP1").toString(), coverter.getMap().get("ROLE_SIMAZ_SP2").toString()};
				System.out.println("Users name " + getClass().getName()+ " for log \n"+Arrays.toString(users) );
				petitService.power_sp1_sp2(dateReport,users);
			}
			
			else { 
				String [] user ={getUserName(),""};
				petitService.power_sp1_sp2(dateReport,user );
			}
		}
		else{
			
			if(insursmo.equals("smo_simaz")){
				String[] users = {coverter.getMap().get("ROLE_SIMAZ_SP1").toString(), coverter.getMap().get("ROLE_SIMAZ_SP2").toString()};
				System.out.println("Users name " + getClass().getName()+ " for log \n"+Arrays.toString(users) );
				petitService.power_sp1_sp2(dateReport,users);
			}
			
			if(insursmo.equals("smo_rosno")){
				String []users = {coverter.getMap().get("ROLE_ROSNO_SP1").toString(),coverter.getMap().get("ROLE_ROSNO_SP2").toString()};
				System.out.println("Users name " + getClass().getName()+ " for log \n"+Arrays.toString(users) );
				petitService.power_sp1_sp2(dateReport,users);
			}
			
			if(insursmo.contains("smo_ingos")){
				String[] users = {coverter.getMap().get("ROLE_INGOS_SP1").toString(),coverter.getMap().get("ROLE_INGOS_SP2").toString()};
				System.out.println("Users name " + getClass().getName()+ " for log \n"+Arrays.toString(users) );
				petitService.power_sp1_sp2(dateReport,users);
			}
		}	
    	return "reporting";
	}
	
	
	/**
	 * Определяет входящие параметры для слоя сервиса. Далее в запрос
	 * Отчет раздела III.Сведения о деятельности страховых представитлей
	 * 
	 * @param dateReport - переменная объекта содержащая данные для поиска 
	 * @param insursmo - выбранная страховая (ТОЛЬКО ДЛЯ ТФОМС)
	 * @param bindingResult
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JRException
	 */
	@RequestMapping(value = "/report_strax3.html", method = RequestMethod.POST)
    public String report_strax3(@ModelAttribute("dateReport") @Valid ReportParams dateReport,@RequestParam(value = "insurcomp",required=false) String insursmo, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
		
		if(insursmo == null){	
			
			if(getRole().contains("ROLE_ROSNO")){
				String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_strax3(dateReport,users);
			}else
			
			if(getRole().contains("ROLE_INGOS")){
				String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_strax3(dateReport,users);
			}else
			
			if(getRole().contains("ROLE_SIMAZ")){
				String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_strax3(dateReport,users);
			}
			
			else {petitService.report_strax3(dateReport, getUserName());}
		}
		else{
			
			if(insursmo.equals("smo_simaz")){
				String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_strax3(dateReport,users);
			}
			
			if(insursmo.equals("smo_rosno")){
				String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_strax3(dateReport,users);
			}
			
			if(insursmo.contains("smo_ingos")){
				String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_strax3(dateReport,users);
			}
		}	
		
    	return "reporting";
	}
	
	
	/**
	 * Определяет входящие параметры для слоя сервиса. Далее в запрос
	 * Отчет по работе экспертизы качества медицинской помощи, проведенной по случаям оказания мп при злокачественных новообразованиях, сопровождающихся
	 * выраженным болевым синдромом
	 * @param dateReport - переменная объекта содержащая данные для поиска 
	 * @param insursmo - выбранная страховая (ТОЛЬКО ДЛЯ ТФОМС)
	 * @param bindingResult
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JRException
	 */
	@RequestMapping(value = "/drugs.html", method = RequestMethod.POST)
    public String report_drugs(@ModelAttribute("dateReport") @Valid ReportParams dateReport,@RequestParam(value = "insurcomp",required=false) String insursmo, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
		
		if(insursmo == null){
			
			if(getRole().contains("ROLE_ROSNO")){
				String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_drugs(dateReport,users);
			}else
			
			if(getRole().contains("ROLE_INGOS")){
				String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_drugs(dateReport,users);
			}else
			
			if(getRole().contains("ROLE_SIMAZ")){
				String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_drugs(dateReport,users);
			}
			
			else {petitService.report_drugs(dateReport, getUserName());}
		}
		else{
			
			if(insursmo.equals("smo_simaz")){
				String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_drugs(dateReport,users);
			}
			
			if(insursmo.equals("smo_rosno")){
				String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_drugs(dateReport,users);
			}
			
			if(insursmo.contains("smo_ingos")){
				String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
				System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
				petitService.report_drugs(dateReport,users);
			}
		}	
    	return "reporting";
	}
	
	@RequestMapping(value = "/report_drugs.html", method = RequestMethod.GET)
    public void report_drugs(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		File f = new File( servletcontext.getRealPath("/resources/report/drugs.xls"));
        downloadFile(request, response, f.getPath());
	}
	
	
	/**
	 * Метод является "транзитом" для экспорта Excel из формы поиска.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/generate/excel.xls", method = RequestMethod.GET)
	public ModelAndView downloadExcel(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		PetitListWrapper petitlistwrapper = (PetitListWrapper) session.getAttribute("list_search");
		List<Petit> listpetit1 = petitlistwrapper.getPetit();
		return new ModelAndView("excelView", "listpetit", listpetit1);
	}
	
	@RequestMapping(value = "/report_call.html", method = RequestMethod.POST)
    public String report_call(@ModelAttribute("dateReport") @Valid ReportParams dateReport, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		
		if(bindingResult.hasErrors()) return "reporting";
		
		if(getRole().contains("ROLE_ROSNO")){
			String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
			System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
			petitService.report_call(dateReport,users);
		}else
		
		if(getRole().contains("ROLE_INGOS")){
			String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
			System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
			petitService.report_call(dateReport,users);
		}else
		
		if(getRole().contains("ROLE_SIMAZ")){
			String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
			System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
			petitService.report_call(dateReport,users);
		}
		
		else {petitService.report_call(dateReport, getUserName());}
		
			
    	return "reporting";
	}
	
	/**
	 * Контродлер отрабатывает логику отчета "отчет по письменным обращениям граждан, поступившим в ТФОМС НСО"
	 * 
	 * @param dateReport  
	 * @param bindingResult
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JRException
	 */
	@RequestMapping(value = "/report_1.html", method = RequestMethod.POST)
    public String report_1(@ModelAttribute("dateReport") @Valid ReportParams dateReport,@RequestParam(value = "insurcomp",required=false) String insursmo, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
			
			if(insursmo == null){	
				
				if(getRole().contains("ROLE_ROSNO")){
					String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
					System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
					petitService.report_1(dateReport,users);
				}else
				
				if(getRole().contains("ROLE_INGOS")){
					String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
					System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
					petitService.report_1(dateReport,users);
				}else
				
				if(getRole().contains("ROLE_SIMAZ")){
					String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
					System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
					petitService.report_1(dateReport,users);
				}
				
				else {petitService.report_1(dateReport, getUserName());}
			}
			else
			{
				if(insursmo.equals("smo_simaz")){
					String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
					System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
					petitService.report_1(dateReport,users);
				}
				
				if(insursmo.equals("smo_rosno")){
					String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
					System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
					petitService.report_1(dateReport,users);
				}
				
				if(insursmo.contains("smo_ingos")){
					String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
					System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
					petitService.report_1(dateReport,users);
				}
			}	
			
    	return "reporting";
	}
	
	@RequestMapping(value = "/report_1_1", method = RequestMethod.GET)
    public void report_1_1(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\pg_form_1_1.xls");
	}
	
	@RequestMapping(value = "/report_1_2", method = RequestMethod.GET)
    public void report_1_2(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\pg_form_1_2.xls");
	}

	
	@RequestMapping(value = "/report_2_1", method = RequestMethod.GET)
    public void report_2_1(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\pg_form_2_1.xls");
	}
	
	@RequestMapping(value = "/downloadreestr", method = RequestMethod.GET)
    public void reestr(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\resources\\doc_fond\\reestr_25.09.2016.docx");
	}
	
	
	@RequestMapping(value = "/downloadreestr1117_1", method = RequestMethod.GET)
    public void reestr1(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\resources\\doc_fond\\all_01.02.2017.xlsx");
	}
	
	@RequestMapping(value = "/downloadmanual", method = RequestMethod.GET)
    public void manual(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\resources\\doc_fond\\manual.docx");
	}

	@RequestMapping(value = "/report_2_3", method = RequestMethod.GET)
    public void report_2_3(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\pg_form_2_3.xls");
	}
	
	@RequestMapping(value = "/report_cc", method = RequestMethod.GET)
    public void report_cc(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\contact_call.xls");
	}
	
	@RequestMapping(value = "/report_cc2", method = RequestMethod.GET)
    public void report_cc2(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\contact_call2.xls");
	}
	
	@RequestMapping(value = "/report_strax3_formreport", method = RequestMethod.GET)
    public void report_strax3_formreport(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		File f = new File( servletcontext.getRealPath("/resources/report/report_strax3.xls"));
        downloadFile(request, response, f.getPath());
	}
	
	
	@RequestMapping(value = "/report_power_sp_file", method = RequestMethod.GET)
    public void report_power_sp_file(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		File f = new File( servletcontext.getRealPath("/resources/report/power_sp1_sp2.xls"));
        downloadFile(request, response, f.getPath());
	}
	
	@RequestMapping(value = "/report_1", method = RequestMethod.GET)
    public void report_1(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		File f = new File( servletcontext.getRealPath("/resources/report/report_letter_appeals.xls"));
        downloadFile(request, response, f.getPath());
	}

	private void downloadFile(HttpServletRequest request,
			HttpServletResponse response, String filePath) throws FileNotFoundException,
			IOException {
		ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
       
        System.out.println("appPath = " + appPath);
 
        String fullPath = "";
        if(filePath.contains("doc_fond")){
        	fullPath = appPath + filePath;
        }else{
        	if(filePath.contains("report_strax3") || filePath.contains("report_letter_") || filePath.contains("drugs") ||
        			filePath.contains("power_sp1_sp2")){
        		fullPath = filePath;
        	}else{
        //String fullPath = appPath + filePath ;      
        		fullPath = "C:\\Appeals3\\Appeal" + filePath ;
        	}
        }
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
 
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
	}
	
	@RequestMapping(value = "/more/{petitId}")
    public String morePetit(@PathVariable("petitId") Integer petitId, ModelMap map) {
		map.put("petit", petitService.getPetit(petitId));
		return "more";
	}
	
	@RequestMapping(value = "/reportAppealPay", method = RequestMethod.POST)
    public String reportAppealPay(@ModelAttribute("dateReport") @Valid ReportParams dateReport, BindingResult bindingResult) throws SQLException, ServletException, IOException, ClassNotFoundException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
		
		if(getRole().contains("ROLE_ROSNO")){
			String users = coverter.getMap().get("ROLE_ROSNO").toString()+coverter.getMap().get("ROLE_ER5002").toString();
			System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
			petitService.reportAppealPay(dateReport,users);
		}else
		
		if(getRole().contains("ROLE_INGOS")){
			String users = coverter.getMap().get("ROLE_INGOS").toString()+coverter.getMap().get("ROLE_ER5003").toString();
			System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
			petitService.reportAppealPay(dateReport,users);
		}else
		
		if(getRole().contains("ROLE_SIMAZ")){
			String users = coverter.getMap().get("ROLE_SIMAZ").toString()+coverter.getMap().get("ROLE_ER5001").toString();
			System.out.println("Users name " + getClass().getName()+ " for log \n"+users );
			petitService.reportAppealPay(dateReport,users);
		}
		
		else {petitService.reportAppealPay(dateReport, getUserName());}
		
    	return "reporting";
	}
	
	@RequestMapping(value = "/reportAppealPayFile", method = RequestMethod.GET)
    public void reportAppealPayFile(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\appeal_pay.xls");
	}
	
	@RequestMapping(value = "/reportConsultOther", method = RequestMethod.POST)
    public String reportConsultOther(@ModelAttribute("dateReport") @Valid ReportParams dateReport, BindingResult bindingResult) throws SQLException, ServletException, IOException, ClassNotFoundException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
		
		petitService.reportConsultOther(dateReport);
    	return "reporting";
	}
	
	@RequestMapping(value = "/reportConsultOtherFile", method = RequestMethod.GET)
    public void reportConsultOtherFile(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\consult_other.xls");
	}
	
	@RequestMapping(value = "/reportCountDetail", method = RequestMethod.POST)
    public String reportCountDetail(@ModelAttribute("dateReport") @Valid ReportParams dateReport, BindingResult bindingResult) throws SQLException, ServletException, IOException, ClassNotFoundException, JRException {
		if(bindingResult.hasErrors()) return "reporting";		
		
    	petitService.reportCountDetail(dateReport);
    	return "reporting";
	}
	
	@RequestMapping(value = "/reportCountDetailFile", method = RequestMethod.GET)
    public void reportCountDetailFile(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        downloadFile(request, response, "\\reports\\count_detail.xls");
	}	
	
   
    
   @RequestMapping(value = "/more/refresh/{petitId}")
    public String moreLoadPetit(@PathVariable("petitId") Integer petitId, ModelMap map) {
    	map.put("petit", petitService.getPetit(petitId));
    	return "petit";
    }
    
    
    
    
    
    @RequestMapping("/delete")
	public @ResponseBody List<Petit> deletePetit(@RequestParam Integer petitId,ModelMap model) {
		petitService.removePetit(petitId);
		List<Petit> pl = petitService.listPetit(getUserName(),getRole());
		for(Petit pt : pl)
    	{
    		pt.setDateInput(pt.getDateInput().substring(8, 10) + "." + pt.getDateInput().substring(5, 7) + "." + pt.getDateInput().substring(0, 4));
    	}
		
	    ModelAndView modelAndView = new ModelAndView();
	    model.addAttribute("petitList", pl);
	    modelAndView.addObject("petitList", pl);
	    
		return pl;
	}
    
    @RequestMapping("/allist")
	public @ResponseBody List<Petit> allPetit(ModelMap model) {
		
		List<Petit> pl = petitService.listPetit(getUserName(),getRole());
		for(Petit pt : pl)
    	{
    		pt.setDateInput(pt.getDateInput().substring(8, 10) + "." + pt.getDateInput().substring(5, 7) + "." + pt.getDateInput().substring(0, 4));
    	}
		
	    ModelAndView modelAndView = new ModelAndView();
	    model.addAttribute("petitList", pl);
	    modelAndView.addObject("petitList", pl);
	    
		return pl;
	}
	
	
	@RequestMapping(value = "/open")
    public @ResponseBody List<Petit> open(@RequestParam Integer petitId,ModelMap model) {
    	
    	Petit pt = petitService.getPetit(petitId);
    	pt.getBlockger2016().setState(3);
    	petitService.addPetit(pt);
    	
    	List<Petit> pl = petitService.listPetit(getUserName(),getRole());
    	
    	for(Petit pt2 : pl)
    	{
    		pt2.setDateInput(pt2.getDateInput().substring(8, 10) + "." + pt2.getDateInput().substring(5, 7) + "." + pt2.getDateInput().substring(0, 4));
    	}
		
	    ModelAndView modelAndView = new ModelAndView();
	    model.addAttribute("petitList", pl);
	    modelAndView.addObject("petitList", pl);
	    
		return pl;
    	
    	
    }
	
	@RequestMapping(value = "/close")
    public @ResponseBody List<Petit>  close(@RequestParam Integer petitId,ModelMap model,String role,String user) {
		
    	petitService.closeAppeal(petitId);
    	List<Petit> pl = new ArrayList<Petit>();
    	/*List<Petit> pl = petitService.listPetit(getUserName());
    	for(Petit pt : pl)
    	{
    		pt.setDateInput(pt.getDateInput().substring(8, 10) + "." + pt.getDateInput().substring(5, 7) + "." + pt.getDateInput().substring(0, 4));
    	}
		*/
    	
	    ModelAndView modelAndView = new ModelAndView();
	    model.addAttribute("petitList", pl);
	    modelAndView.addObject("petitList", pl);
	    
		return pl;
    }
	
	@RequestMapping(value = "/close_search")
    public @ResponseBody List<Petit>  close_search(@RequestParam Integer petitId,ModelMap model) {
		
    	petitService.closeAppeal(petitId);
    	List<Petit> pl = new ArrayList<Petit>();
    	/*List<Petit> pl = petitService.listPetit(getUserName());
    	for(Petit pt : pl)
    	{
    		pt.setDateInput(pt.getDateInput().substring(8, 10) + "." + pt.getDateInput().substring(5, 7) + "." + pt.getDateInput().substring(0, 4));
    	}
		*/
    	
	    ModelAndView modelAndView = new ModelAndView();
	    model.addAttribute("petitList", pl);
	    modelAndView.addObject("petitList", pl);
	    
		return pl;
    }
	
	/**
	 * Метод выщитывает дату планового ответа (дату предельного ответа после которой пойдет просрочка ответа по обращению)
	 * @param petit - ссылка на объект в котором устанавливается дата
	 * @return
	 * @throws ParseException
	 */
	private Petit createDatePlan(Petit petit) throws ParseException{

    	DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Calendar cal  = Calendar.getInstance();
		cal.setTime(df.parse(petit.getDateInput()));
    	
    	if(petit.getPresentId() == 1){
    		
    			Calendar startdate_plus = utilitys.daysPlus((Calendar)cal.clone(), 30,0);
				while(petitService.isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus(startdate_plus, 1,0);
				};
				
				petit.getBlockger2016().setDate_plan_end(df.format(startdate_plus.getTime()));
    	}
    	
    	if(petit.getPresentId() == 2){
    	
    		if(petit.getBlockger2016().getDate_end() == null && petit.getBloutboindletter2016().getDate_between().equals("")){
    		
				Calendar startdate_plus = utilitys.daysPlus((Calendar)cal.clone(), 30,0);
				while(petitService.isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus(startdate_plus, 1,0);
				};
				
				petit.getBlockger2016().setDate_plan_end(df.format(startdate_plus.getTime()));
				
    		}
    		
    		if(petit.getBlockger2016().getDate_end() == null && !petit.getBloutboindletter2016().getDate_between().equals("")){
    			
    			Calendar startdate_plus = utilitys.daysPlus(cal, 60,0);
				while(petitService.isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus(startdate_plus, 1,0);
				};
				
				petit.getBlockger2016().setDate_plan_end(df.format(startdate_plus.getTime()));
    		}
    		if(petit.getBlockger2016().getDate_end() != null && petit.getBloutboindletter2016().getDate_between().equals("")){
    			
    			Calendar startdate_plus = utilitys.daysPlus((Calendar)cal.clone(), 30,0);
				while(petitService.isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus(startdate_plus, 1,0);
				};
				
				petit.getBlockger2016().setDate_plan_end(df.format(startdate_plus.getTime()));
				
    		}
    		if(petit.getBlockger2016().getDate_end() != null && !petit.getBloutboindletter2016().getDate_between().equals("")){
    			
    			Calendar startdate_plus = utilitys.daysPlus(cal, 60,0);
				while(petitService.isCeleb(startdate_plus.getTime())){
					startdate_plus = utilitys.daysPlus(startdate_plus, 1,0);
				};
				
				petit.getBlockger2016().setDate_plan_end(df.format(startdate_plus.getTime()));
    		}
    		
    	}
	
    	return petit;
	}
	
	
	/**
	 * Метол проверяет на валидность объект который "прищел" с клиента. Так как у нас проверка валидности есть еще и на клиенте то этот метол в основном нужен для
	 * проверки REST запросов, то есть которые пришли не с клиента (непосредственно сайта) а обычным http запросом
	 * @param para - флаг нажатой кнопки
	 * @param petit - ссылка на объект который пришел с REST
	 * @return
	 * @throws ValidationForRest
	 */
	private Petit validRest(String para,Petit petit) throws ValidationForRest{
		
		if(para.trim().equals("Завершить")){
    		
    		if(petit.getTypeId() == 0) { throw new ValidationForRest("The type is requare field");}
			else if(petit.getCauseId() == 0){throw new ValidationForRest("The CauseId is requare field");}
			else if((petit.getTypeId() == 1 && petit.getCauseId() == 2 && petit.getRectif1Id() == 0) ||
					(petit.getTypeId() == 1 && petit.getCauseId() == 4 && petit.getRectif1Id() == 0) ||
					(petit.getTypeId() == 1 && petit.getCauseId() == 11 && petit.getRectif1Id() == 0)||
					(petit.getTypeId() == 1 && petit.getCauseId() == 13 && petit.getRectif1Id() == 0)){
				throw new ValidationForRest("The Rectif1Id is requare field");
			}
			else if(petit.getBlockger2016().getInbound_from() == null && petit.getTypeId()== 1 && petit.getPresentId() == 2){throw new ValidationForRest("The getInbound_from is requare field");}
			else if((petit.getBloutboindletter2016().getDate_redirect() != null &&
					!petit.getBloutboindletter2016().getDate_redirect().equals("") ||
					petit.getBloutboindletter2016().getRedirect_adress() !=null &&
					!petit.getBloutboindletter2016().getRedirect_adress().equals("0"))
					&&
					(petit.getBloutboindletter2016().getDate_redirect().equals("") ||
					 petit.getBloutboindletter2016().getRedirect_adress().equals("0"))
					){throw new ValidationForRest("The Redirect_adress && Date_redirect is requare field");}
			else if(petit.getPresentId() == 2 &&
					!petit.getBloutboindletter2016().getRedirect_adress().equals("0") &&
			 		!petit.getBloutboindletter2016().getDate_redirect().equals("")
			 		&&
			 		(petit.getBloutboindletter2016().getDate_response().equals("") || petit.getBloutboindletter2016().getResponsible().equals(""))
			 		){throw new ValidationForRest("The getResponsible is requare field");}		
			else if(petit.getMoId() !=0 &&
					(petit.getHspId() == 0 || petit.getBlockger2016().getTypempid() == 0)){
				throw new ValidationForRest("The getTypempid is requare field");
			}
	}
		return petit;
	}
	
   @PostMapping("/fileUpload_hotcall")
   public ResponseEntity<Object> fileUpload(@RequestParam(value = "_csrf", required = false) String csrf, @RequestParam("file") MultipartFile file)
         throws IOException, JAXBException {

      // Save file on system
      if (!file.getOriginalFilename().isEmpty()) {
    	  
    	 String pathStoreUploadedFile = Option.getDirectory("directory","directories.properties");
    	 
    	 File f = new File(pathStoreUploadedFile, file.getOriginalFilename());
         BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(f));
         outputStream.write(file.getBytes());
         outputStream.flush();
         outputStream.close();
         
         List<Petit> pt = petitService.parseArchiveFile(f);
         
         for(Petit p : pt) petitService.addPetit(p);
         
      }else{
         return new ResponseEntity<>("Файл не загружен. Повторите попытку или обратитесь к администратору.",HttpStatus.BAD_REQUEST);
      }
      
      return new ResponseEntity<>("Файл успешно загружен в базу.",HttpStatus.OK);
   }
}
