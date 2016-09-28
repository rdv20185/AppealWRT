package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import res.Fields;
import res.TransferFiles;
import service.PetitService;
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
import ftp.FTPDownloadFileDemo;

@Controller
public class PetitController {
	
	@Autowired
    private PetitService petitService;
	
    public PetitController() {
    	
    }
   
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody List<Petit> addPetit(Petit petit,String submitted,HttpServletRequest request,ModelMap model) throws ParseException, InterruptedException, IOException {

    	
    	/*petit.setSurname(new String(petit.getSurname().getBytes("ISO-8859-1"),"UTF-8"));
    	petit.setName(new String(petit.getName().getBytes("ISO-8859-1"),"UTF-8"));
    	petit.setPatrony(new String(petit.getPatrony().getBytes("ISO-8859-1"),"UTF-8"));
    	if(petit.getLetterNum() !=null)
    	petit.setLetterNum(new String(petit.getLetterNum().getBytes("ISO-8859-1"),"UTF-8"));
    	if(petit.getLetterDate() !=null)
    	petit.setLetterDate(new String(petit.getLetterDate().getBytes("ISO-8859-1"),"UTF-8"));
    	petit.setPolicy(new String(petit.getPolicy().getBytes("ISO-8859-1"),"UTF-8"));
    	petit.setAdress(new String(petit.getAdress().getBytes("ISO-8859-1"),"UTF-8"));
    	if(petit.getSatisf() !=null)
    	petit.setSatisf(new String(petit.getSatisf().getBytes("ISO-8859-1"),"UTF-8"));
    	if(petit.getCompens() !=null)
    	petit.setCompens(new String(petit.getCompens().getBytes("ISO-8859-1"),"UTF-8"));
    	if(petit.getCompensSource() !=null)
    	petit.setCompensSource(new String(petit.getCompensSource().getBytes("ISO-8859-1"),"UTF-8"));
    	if(petit.getCompensCode() !=null)
    	petit.setCompensCode(new String(petit.getCompensCode().getBytes("ISO-8859-1"),"UTF-8"));
    	if(petit.getCompensSum() !=null)
    	petit.setCompensSum(new String(petit.getCompensSum().getBytes("ISO-8859-1"),"UTF-8"));
    	if(petit.getCauseNote() !=null)
    	petit.setCauseNote(new String(petit.getCauseNote().getBytes("ISO-8859-1"),"UTF-8"));
    	petit.setUsername(new String(petit.getUsername().getBytes("ISO-8859-1"),"UTF-8"));
    	
    	
    	petit.getBloutboindletter2016().setResponsible(new String(petit.getBloutboindletter2016().getResponsible().getBytes("ISO-8859-1"),"UTF-8"));
    	petit.getBloutboindletter2016().setNumOutLetter(new String(petit.getBloutboindletter2016().getNumOutLetter().getBytes("ISO-8859-1"),"UTF-8"));
    	
    	petit.getBloutboindletter2016().getMany().get(0).setNote(new String(petit.getBloutboindletter2016().getMany().get(0).getNote().getBytes("ISO-8859-1"),"UTF-8"));
    	petit.getBloutboindletter2016().getMany().get(1).setNote(new String(petit.getBloutboindletter2016().getMany().get(1).getNote().getBytes("ISO-8859-1"),"UTF-8"));
    	petit.getBloutboindletter2016().getMany().get(2).setNote(new String(petit.getBloutboindletter2016().getMany().get(2).getNote().getBytes("ISO-8859-1"),"UTF-8"));
    	*/
    	
    	String para = submitted;//new String(submitted.getBytes("ISO-8859-1"),"UTF-8");
    	/* ловим с клиЕнта нажатую кнопку
    	 * ЕСЛИ с клиента прилетает письменное обращение petit.getConectId() ==2  и дата исходящего пустая getDate_response() то статус = 2(в работе) и date_end = ""
    	 * Если письменное и дата ответа не пустая то статус = 3
    	 */
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
    
    	private @ResponseBody List<Petit> adds(Petit petit,HttpServletRequest request,String submitted,ModelMap model) throws UnsupportedEncodingException {
		
		/*
		 * Ловим с клиента в переменную ff поле date_end
		 */
		String ff = request.getParameter("fil");
		if(ff !=null && !ff.equals(""))
		{
    		Date date = new Date();
  		  	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
  		  	try { date = df.parse(ff); } catch (ParseException e) { e.printStackTrace(); }
    		petit.getBlockger2016().setDate_end(date);
		}
		String para =submitted;//new String(submitted.getBytes("ISO-8859-1"),"UTF-8");
		
		/* Если нажата кнопка сохранить то в поле username добавляется ключ (ключ приходит с клиента input select - "назначить")
		 * Ключ - это значение при котором записи из базы будут доступны определенным группам пользователей
		 */
		/*
		 * Обрабатывается нажатие клавиши назначить в режиме редактирования ночным 
		 */
	   	
		System.out.println("hjdthrf "+petit.getPresentId()+" "+para.trim()+" "+petit.getBlockger2016().getState());
		
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
	        		try { petit.getBlockger2016().setDate_end(df.parse(petit.getBloutboindletter2016().getDate_response().concat(" 01:00:00.123")));} catch (ParseException e) {
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

	
	@RequestMapping(value = "/report", method = RequestMethod.POST)
    public String report(@ModelAttribute("dateReport") @Valid ReportParams dateReport,@RequestParam(value = "insurcomp",required=false) String insursmo, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
		if(insursmo == null){	
			if(getUserName().contains("smo_rosno")) petitService.pgForm(dateReport, "call5002callnight5002smo_rosnosmo_rosno_01smo_rosno_02smo_rosno_03smo_rosno_04smo_rosno_05smo_rosno_06smo_rosno_07smo_rosno_08smo_rosno_09smo_rosno_10smo_rosno_11smo_rosno_12smo_rosno_13smo_rosno_14smo_rosno_15smo_rosno_16smo_rosno_17smo_rosno_18smo_rosno_19smo_rosno_20");
			else if(getUserName().equals("smo_ingos")) petitService.pgForm(dateReport, "smo_ingoscall5003callnight5003");
			else if(getUserName().equals("smo_simaz")) petitService.pgForm(dateReport, "smo_simazcall5001callnight5001");
			else {petitService.pgForm(dateReport, getUserName());}
		}
		else
		{
			if(insursmo.equals("smo_simaz")){petitService.pgForm(dateReport, "smo_simazcall5001callnight5001");}
			if(insursmo.equals("smo_rosno")){petitService.pgForm(dateReport, "call5002callnight5002smo_rosnosmo_rosno_01smo_rosno_02smo_rosno_03smo_rosno_04smo_rosno_05smo_rosno_06smo_rosno_07smo_rosno_08smo_rosno_09smo_rosno_10smo_rosno_11smo_rosno_12smo_rosno_13smo_rosno_14smo_rosno_15smo_rosno_16smo_rosno_17smo_rosno_18smo_rosno_19smo_rosno_20");}
			if(insursmo.equals("smo_ingos")){petitService.pgForm(dateReport, "smo_ingoscall5003callnight5003");}
		}	
    	return "reporting";
	}
	
	@RequestMapping(value = "/report_call", method = RequestMethod.POST)
    public String report_call(@ModelAttribute("dateReport") @Valid ReportParams dateReport, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting";
		
			if(getUserName().contains("smo_rosno")) petitService.report_call(dateReport, "smo_rosnocall5002callnight5002smo_rosno_01smo_rosno_02smo_rosno_03smo_rosno_04smo_rosno_05smo_rosno_06smo_rosno_07smo_rosno_08smo_rosno_09smo_rosno_10smo_rosno_11smo_rosno_12smo_rosno_13smo_rosno_14smo_rosno_15smo_rosno_16smo_rosno_17smo_rosno_18smo_rosno_19smo_rosno_20");
			else if(getUserName().equals("smo_ingos")) petitService.report_call(dateReport, "smo_ingoscall5003callnight5003");
			else if(getUserName().equals("smo_simaz")) petitService.report_call(dateReport, "smo_simazcall5001callnight5001");
			else {petitService.report_call(dateReport, getUserName());}
		
			
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

	private void downloadFile(HttpServletRequest request,
			HttpServletResponse response, String filePath) throws FileNotFoundException,
			IOException {
		ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);
 
        //String fullPath = appPath + filePath ;      
        String fullPath = "D:\\Appeals3\\Appeal" + filePath ;
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
		
		petitService.reportAppealPay(dateReport, getUserName());
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
		List<Petit> pl = petitService.listPetit(getUserName());
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
		
		List<Petit> pl = petitService.listPetit(getUserName());
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
    	
    	List<Petit> pl = petitService.listPetit(getUserName());
    	
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
    public @ResponseBody List<Petit>  close(@RequestParam Integer petitId,ModelMap model) {
		
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
}
