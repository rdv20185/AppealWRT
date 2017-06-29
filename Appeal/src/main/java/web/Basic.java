package web;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.*;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.BlockGER2016;
import domain.Cause;
import domain.Conect;
import domain.Hsp;
import domain.Insur;
import domain.Mo;
import domain.Petit;
import domain.Present;
import domain.Rectif1;
import domain.Rectif2;
import domain.Rectif3;
import domain.Rectif4;
import domain.Source;
import domain.Subtype;
import domain.Ter;
import domain.Type;
import domain.blOutboindLETTER2016;
import res.Fields;
import res.TransferFiles;
import service.PetitListWrapper;
import service.PetitService;
import util.Util;
import util.Utilitys;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.stream.Collectors;
import service.xml.Converter;

@Controller
public class Basic {
	
	Map<Integer, String> source1 = new HashMap<Integer, String>();
	Map<Integer, String> source2 = new HashMap<Integer, String>();
	Map<Integer, String> source3 = new HashMap<Integer, String>();
	{
		
		source1.put(1, "ТФОМС");
		source2.put(2, "СМО");
		source3.put(3, "ЕР НСО");
		source3.put(4, "CallNight");
	}
	
	@Autowired
    private PetitService petitService;
	@Autowired
    private Utilitys utilitys;
	@Autowired
	Converter coverter;
	
	//@ModelAttribute
	public  ModelMap setupForm(ModelMap map,HttpServletRequest request,Petit petit) throws UnsupportedEncodingException {
		
    	map.put("petit", petit);
    	
    	if(getRole().contains("ROLE_ADMIN")){
			map.put("sourceList", source1);
			map.put("presentList", Fields.getPresent());
			map.put("conectList", Fields.getConect());
			map.put("listassign", Fields.getProperties());
			map.put("responsible" , coverter.getMap().get("ROLE_ADMIN"));
		}
    
    	if(getRole().contains("ROLE_TFOMS")){
			map.put("sourceList", source1);
			map.put("presentList", Fields.getPresent());
			map.put("conectList", Fields.getConect());
			map.put("listassign", Fields.getfirsttfoms());
			map.put("responsible" , coverter.getMap().get("ROLE_TFOMS"));
		}
    	
    	if(getRole().contains("ROLE_ER"))
		{
			if(getUserName().equals("ernso")){map.put("sourceList", source3);}else{map.put("sourceList", source2);}
			map.put("listassign", Fields.getProperties());
			map.put("conectList", Fields.getConectforFL());
			map.put("presentList", Fields.getPresentforFL());
			
		}
    		
    	if(getRole().contains("ROLE_SIMAZ") && !getRole().contains("ROLE_TECH_ER")){
    		map.put("listassign", Fields.getfirstsimaz());
    		map.put("sourceList", source2);
    		map.put("conectList", Fields.getConect());
			map.put("presentList", Fields.getPresent());
			map.put("responsible" , coverter.getMap().get("ROLE_SIMAZ_SP2"));
    	}
				
		if(getRole().contains("ROLE_ROSNO") && !getRole().contains("ROLE_TECH_ER")){
			map.put("listassign", Fields.getfirstrosno());
			map.put("sourceList", source2);
			map.put("conectList", Fields.getConect());
			map.put("presentList", Fields.getPresent());
			map.put("responsible" , coverter.getMap().get("ROLE_ROSNO_SP2"));
		}
		if(getRole().contains("ROLE_INGOS") && !getRole().contains("ROLE_TECH_ER")){
			map.put("listassign", Fields.getfirstingos());
			map.put("sourceList", source2);
			map.put("conectList", Fields.getConect());
			map.put("presentList", Fields.getPresent());
			map.put("responsible" , coverter.getMap().get("ROLE_INGOS_SP2"));
		}

		
		map.put("inbound_fromList", Fields.getInbound_from());
    	map.put("intermedList", Fields.getIntermed());
    	map.put("typeList", Fields.getType());
    	map.put("terList", Fields.getTer());
    	map.put("moList", Fields.getMo());
    	map.put("insurList", Fields.getInsur());
    	map.put("placeList", Fields.getPlace());
    	map.put("validList", Fields.getValid());
    	map.put("hspList", Fields.getHsp());
    	map.put("typeMP", Fields.getTypeMP());
    	
    	//map.put("dateReport", new ReportParams());
    	
		return map;
	}

    @RequestMapping("/index")
    public String listPetits(Map<String, Object> map,HttpServletRequest request,ModelMap mapm) throws UnsupportedEncodingException {
    	
    	setupForm(mapm,request,new Petit());
    	nightcallsprocess(request);
    	
    	List<Petit> pl = petitService.listPetit(getUserName(),getRole());
    	StringBuilder sb = new StringBuilder();
    	for(Petit pt : pl)
    	{
    		if(pt.getDateInput() !=null){
    			sb.append(pt.getDateInput().substring(8, 10));
    			sb.append(".");
    			sb.append(pt.getDateInput().substring(5, 7));
    			sb.append(".");
    			sb.append(pt.getDateInput().substring(0, 4));
    			pt.setDateInput(sb.toString());
    			sb.delete(0, sb.length());
    		}
    		// + "." +  + "." + );
    	}
        map.put("petitList", pl);
    	
        return "petit";
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
    
    private void nightcallsprocess(HttpServletRequest request){
    	String path = request.getServletContext().getRealPath("/")+"night_calls_working";
    	String path_worked =/*request.getServletContext().getRealPath("/")+"night_calls_worked"; */"D:/Java/Tomcat7/TomCat7/night_calls_worked/"; 
    	File f = new File(path);
    	if(f.isAbsolute()){
    		if(f.list().length != 0){
	    		String []d =f.list();
	    		for(int i=0;i < d.length; i++){
	    			if(d[i].contains(".wav")){
	    				String tel = parsenumTel(d[i]);
	    				// вытаскиваем дату
	    				String ff = d[i].substring(0,d[i].indexOf("_"));
	    				//check the day
	    				String day = ff.substring(ff.indexOf("-",5), ff.length()); 
						if(day.length() ==2){  // e.g -4 or -9, not -25 or -18 etc
							day = day.replace("-", "0");
						}else{ if(day.length() ==3) {day = day.replace("-", "");}}
	    				// check the mounth
						String mounth = "";
	    				if(ff.substring(1+ff.indexOf("-"), ff.indexOf("-", 1+ff.indexOf("-"))).length() == 1)
	    				{
	    					mounth = ".0"+ff.substring(1+ff.indexOf("-"), 2+ff.indexOf("-"));
	    				}else{
	    					mounth = "."+ff.substring(1+ff.indexOf("-"), ff.indexOf("-", 1+ff.indexOf("-")));
	    				}
	    				// get year
	    				String year = "."+ff.substring(0, 4);
	    				ff = day+mounth+year;
	    				System.out.println("fff "+ff+"  ");
	    				
	    				new TransferFiles().copy(path + File.separator + d[i], path_worked + File.separator + d[i]);
	    				new TransferFiles().delete(path + File.separator + d[i]);
	    				
	    				Petit petit = new Petit();
	    				//petit.setTypeId(3);
	    				//petit.setCauseId(35);
	    				petit.setConectId(7);
	    				petit.setPresentId(1);
	    				petit.setDateInput(ff);
	    				petit.setTel(tel);
	    				petit.setUsername("auto");
	    				BlockGER2016 blo = new BlockGER2016();
	    				blo.setRegname("auto");
	    				blo.setRegsource_id(4);
	    				blo.setFilecall(path_worked + File.separator + d[i]);
	    				blo.setState(1);
	    				petit.setBlockger2016(blo);
	    				petit.getBlockger2016().setPetit(petit);
	    				System.out.println("Petit "+petit);
	    				petitService.addPetit(petit);
	    			}
	    		}
    		}else{System.out.println("equals 0");}	
    	}else{}
    	//System.out.println("WWWWWWW "+path);
    	
    }
    
    private String parsenumTel(String val){
    	
		// вытаскиваем дату
    	char []t = val.toCharArray();
    	int count = 0;
    	int startsubstr = 0;
    	int endsubstr = 0;
    	for (int j = 0; j < t.length; j++) {
			if(t[j] == '_') count++;
			if(count ==2 && t[j] == '_'){
				startsubstr = j;
			}
			if(count == 3 && t[j] == '_'){
				endsubstr = j;
			}
		}
		String ff = val.substring(startsubstr+1,endsubstr);
		
		return ff;
    }
	
    
    @RequestMapping(value = "/refresh/{petitId}")
    public String loadPetit(@PathVariable("petitId") Integer petitId, ModelMap map,HttpServletRequest request) throws UnsupportedEncodingException {
    	setupForm(map,request, new Petit());
    	map.put("petit", petitService.getPetit(petitId));
    	
    	return "petit";
    }
    
    @RequestMapping(value = "/refresh/add", method = RequestMethod.POST)
    public String refreshAddPetit(@ModelAttribute("petit") @Valid Petit petit, BindingResult bindingResult,HttpServletRequest request, ModelMap mapm) throws UnsupportedEncodingException, ParseException, InterruptedException {
    {
    	
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
		    	
	    		if(petit.getBloutboindletter2016().getDate_between().equals("")){
	    		
					Calendar startdate_plus = utilitys.daysPlus((Calendar)cal.clone(), 30,0);
					while(petitService.isCeleb(startdate_plus.getTime())){
						startdate_plus = utilitys.daysPlus(startdate_plus, 1,0);
					};
					
					petit.getBlockger2016().setDate_plan_end(df.format(startdate_plus.getTime()));
					
	    		}
	    		
	    		if(!petit.getBloutboindletter2016().getDate_between().equals("")){
	    			Calendar startdate_plus = utilitys.daysPlus(cal, 60,0);
					while(petitService.isCeleb(startdate_plus.getTime())){
						startdate_plus = utilitys.daysPlus(startdate_plus, 1,0);
					};
					
					petit.getBlockger2016().setDate_plan_end(df.format(startdate_plus.getTime()));
	    		}
	    	}
    	}
    	
    	
    	String pa = request.getParameter("submit");
    	
    	if(pa.trim().equals("Завершить") || pa.trim().equals("Изменить")){
    		
    		if(petit.getTypeId() == 0){bindingResult.rejectValue("typeId", "error.petit", "Поле Тип обязательно для заполнения");}
    		if(petit.getMoId() != 0 && petit.getBlockger2016().getTypempid() == 0){bindingResult.rejectValue("typeId", "error.petit", "Поле Вид МП обязательно для заполнения при заполненом поле МО");}
    		if(petit.getCauseId() == 0){bindingResult.rejectValue("causeId", "error.petit", "Поле Причина обязательно для заполнения");}
    		if((petit.getTypeId() == 1 && petit.getCauseId() == 2 && petit.getRectif1Id() ==0) ||
			   (petit.getTypeId() == 1 && petit.getCauseId() == 4 && petit.getRectif1Id() ==0) ||
			   (petit.getTypeId() == 1 && petit.getCauseId() == 11 && petit.getRectif1Id() ==0)||
			   (petit.getTypeId() == 1 && petit.getCauseId() == 13 && petit.getRectif1Id() ==0)){bindingResult.rejectValue("Rectif1Id", "error.petit", "Поле 'Уточнение1' обязательно для заполнения");}
    		
    		
    		if(petit.getBlockger2016().getRegname().contains("call") && petit.getConectId() != 7	){bindingResult.rejectValue("causeId", "error.petit", "У Вас недостаточно прав изменить поле 'Связь' или поле 'Cвязь' имеет неправильное значение отличное от значения 'Горячая линия' ");}
    		// доработать
    		if(petit.getConectId() == 7	&& !petit.getBlockger2016().getRegname().contains("call") &&  !petit.getBlockger2016().getRegname().contains("auto") &&
    		   !petit.getBlockger2016().getRegname().contains("vasilyeva") &&
    		   !petit.getBlockger2016().getRegname().contains("smyvin")){bindingResult.rejectValue("causeId", "error.petit", "У Вас недостаточно прав для выбора в поле 'Cвязь' значения 'Горячая линия' ");}
    		
    		if(bindingResult.hasErrors()) { setupForm(mapm,request,petit); return "petit"; }
    	}
    	
    	
    	if(pa.trim().equals("Завершить")){
    		if(petit.getPresentId() == 2 && petit.getBloutboindletter2016().getDate_response().equals("")){
    			petit.getBlockger2016().setState(2);
    			if(petit.getBloutboindletter2016().getResponsible().equals("")){ petit.setUsername(getUserName());}
        		else{petit.setUsername(petit.getBloutboindletter2016().getResponsible());}
    		}
    		else if(petit.getPresentId() == 2 && !petit.getBloutboindletter2016().getDate_response().equals("")){
    			petit.getBlockger2016().setState(3);
    			DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
        		try { petit.getBlockger2016().setDate_end(df.parse(petit.getBloutboindletter2016().getDate_response().concat(" 01:00:00.123")));} catch (ParseException e) {
					e.printStackTrace();
				}
        		if(petit.getBloutboindletter2016().getResponsible().equals("")){ petit.setUsername(getUserName());}
        		else{petit.setUsername(petit.getBloutboindletter2016().getResponsible());}
    		}else{
    			petit.getBlockger2016().setState(3);
    			petit.getBlockger2016().setDate_end(new Date());
    		}
    	}
		return addsold(petit, bindingResult,request);
	}
    
    private String addsold(Petit petit, BindingResult bindingResult,HttpServletRequest request) {
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
		String para = request.getParameter("submit");
		
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
		
				// Add entity to subtype
				petitService.deleteSubType(petit.getId());
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
		return "redirect:/index";
	}
    
    @RequestMapping("/searching")
    public String searching(ModelMap map,HttpServletRequest request) throws UnsupportedEncodingException {
    	setupForm(map,request,new Petit());
    	map.put("petit", new Petit());
        return "searching";
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@ModelAttribute("petit") Petit petit, ModelMap map,HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(required=false) String searchcheckinbound,
    		@RequestParam(required=false) String overdueappeal,
    		HttpSession session) throws Throwable {
    	
    	//searching parameters
    	int t = petit.getTypeId(), c = petit.getCauseId(), 
    	r1 = petit.getRectif1Id(), r2 = petit.getRectif2Id(), r3 = petit.getRectif3Id(), r4 = petit.getRectif4Id();
    	
		if(t != 0) petit.setType(new Type(this.petitService.getTypes().get(t).getName(),t));
		if(c != 0) petit.setCause(new Cause(this.petitService.getTypes().get(t).getCause(c).getName(),c));
		if(r1 != 0) petit.setRectif1(new Rectif1(this.petitService.getCauses().get(c).getRectif1(r1).getName(),r1));
		if(r2 != 0) petit.setRectif2(new Rectif2(this.petitService.getRectifs1().get(r1).getRectif2(r2).getName(),r2));
		if(r3 != 0) petit.setRectif3(new Rectif3(this.petitService.getRectifs2().get(r2).getRectif3(r3).getName(),r3));
		if(r4 != 0) petit.setRectif4(new Rectif4(this.petitService.getRectifs3().get(r3).getRectif4(r4).getName(),r4));
		if(petit.getSourceId() != 0) petit.setSource(new Source(Fields.getSource().get(petit.getSourceId()), petit.getSourceId()));
		if(petit.getPresentId() != 0) petit.setPresent(new Present(Fields.getPresent().get(petit.getPresentId()), petit.getPresentId()));
		if(petit.getConectId() != 0) petit.setConect(new Conect(Fields.getConect().get(petit.getConectId()), petit.getConectId()));
		if(petit.getTerId() != 0) petit.setTer(new Ter(Fields.getTer().get(petit.getTerId()), petit.getTerId()));
		if(petit.getTerAnswerId() != 0) petit.setTerAnswer(new Ter(Fields.getTer().get(petit.getTerAnswerId()), petit.getTerAnswerId()));
		if(petit.getMoId() != 0) petit.setMo(new Mo(Fields.getMo().get(petit.getMoId()), petit.getMoId()));
		if(petit.getInsurId() != 0) petit.setInsur(new Insur(Fields.getInsur().get(petit.getInsurId()), petit.getInsurId()));
		if(petit.getValidId() != 0) petit.setValid(new domain.Valid(Fields.getValid().get(petit.getValidId()), petit.getValidId()));
		if(petit.getHspId() != 0) petit.setHsp(new Hsp(Fields.getHsp().get(petit.getHspId()), petit.getHspId()));
		//if(!petit.getBlockger2016().getClaim_inshur().equals("0")) petit.getBlockger2016().setClaim_inshur("1");
		map.put("petitParam", petit);

		//searching
		petitService.setSearchParams(petit);
		List<Petit> listPetit = petitService.listSearch(getUserName(),searchcheckinbound,overdueappeal,getRole());
		
		//petitService.createDate_plan(listPetit);
		listPetit = processListPetit(listPetit,overdueappeal);

		/*временный блок
		 * 
		 *	listPetit = processListPetit(listPetit,overdueappeal); 
		 * 
		 * */
		
		/*DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
		Calendar cal  = Calendar.getInstance();
		Calendar cal2  = Calendar.getInstance();
		for (int i = 0; i < listPetit.size(); i++) {
			if (listPetit.get(i).getBloutboindletter2016() == null) listPetit.get(i).setBloutboindletter2016(new blOutboindLETTER2016());
			if (listPetit.get(i).getBloutboindletter2016().getDate_between() == null) listPetit.get(i).getBloutboindletter2016().setDate_between("");
			listPetit.get(i).setDateInput(listPetit.get(i).getDateInput().substring(0, 11));
			
			if(overdueappeal == null){}
			else if(overdueappeal != null){
				
					if(overdueappeal != null && listPetit.get(i).getBlockger2016().getDate_end() != null && listPetit.get(i).getBloutboindletter2016().getDate_between().equals("")){
						cal.setTime(df.parse(listPetit.get(i).getDateInput().substring(0, 11).trim()));
						cal2.setTime(listPetit.get(i).getBlockger2016().getDate_end());
						
						if(Util.daysBetween(cal, cal2) < 31){
							if(i == 0){listPetit.remove(i); i = 0;}
							else{listPetit.remove(i);	i = i-1;}
						}
					}
					else if(overdueappeal != null && listPetit.get(i).getBlockger2016().getDate_end() != null && listPetit.get(i).getBloutboindletter2016().getDate_between().length() > 1){
								cal.setTime(df2.parse(listPetit.get(i).getBloutboindletter2016().getDate_between().trim()));
								cal2.setTime(listPetit.get(i).getBlockger2016().getDate_end());
								
								if(Util.daysBetween(cal, cal2) < 31){
									if(i == 0){listPetit.remove(i); i = 0;}
									else{listPetit.remove(i);	i = i-1;}
								}
					}
					else if(overdueappeal != null && listPetit.get(i).getBlockger2016().getDate_end() == null && listPetit.get(i).getBloutboindletter2016().getDate_between().length() > 1 ){
						cal.setTime(df.parse(listPetit.get(i).getDateInput().substring(0, 11).trim()));
						cal2.setTime(df2.parse(listPetit.get(i).getBloutboindletter2016().getDate_between().trim()));
						
						if(Util.daysBetween(cal, cal2) < 31){
							if(i == 0){listPetit.remove(i); i = 0;}
							else{listPetit.remove(i);	i = i-1;}
						}
					}
					else if(overdueappeal != null && listPetit.get(i).getBlockger2016().getDate_end() == null && listPetit.get(i).getBloutboindletter2016().getDate_between().equals("") ){
						cal.setTime(df.parse(listPetit.get(i).getDateInput().substring(0, 11).trim()));
						cal2.setTime(new Date());
						
						if(Util.daysBetween(cal, cal2) < 31){
							if(i == 0){listPetit.remove(i); i = 0;}
							else{listPetit.remove(i);	i = i-1;}
						}
					}
					
					if(i == listPetit.size()-1){	listPetit.remove(0); i = i-1;}
					
			}		
		}*/
		
		/*	/временный блок конец
		 * 
		 * 
		 * 
		 * */
		

		
		PetitListWrapper pt = new PetitListWrapper();
		pt.setPetit(listPetit);
		session.setAttribute("list_search", pt);
		
		if(listPetit.size() <= 10000) {
			map.put("searchList", listPetit);
			map.put("searchListSize", listPetit.size());
		} else map.put("searchListSize", "более 10000");
		
		setupForm(map,request,petit);
		
		return"searching";
	}
    
    
    /**
     * Метод удаляет объекты из коллекции которые не удовлетворяют условиям логики.
     * Проверяеи поля дат. "До" или "После" одна дата относительно другой
     * @param listPetit - коллекция с объектами
     * @param overdueappeal - флаг "просроченные сообщения"
     * @return - коллекцию с актуальными объектами (которые удовлетворяют критерию поиска)
     * @throws ParseException
     */
    private List<Petit> processListPetit(List<Petit> listPetit, String overdueappeal) throws ParseException{
    	
    	if(overdueappeal != null){
    		
	    	Calendar enddate = null;
			Calendar startdate_plus = null;
			DateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
	    	
	    	for (int i = 0; i < listPetit.size(); i++) {
	    		
				if (listPetit.get(i).getBloutboindletter2016() == null) listPetit.get(i).setBloutboindletter2016(new blOutboindLETTER2016());
				if (listPetit.get(i).getBloutboindletter2016().getDate_between() == null) listPetit.get(i).getBloutboindletter2016().setDate_between("");
				listPetit.get(i).setDateInput(listPetit.get(i).getDateInput().substring(0, 11));
			    
				if(utilitys.valid(overdueappeal,listPetit.get(i)) == 1){
					utilitys.processDate(listPetit.get(i));
					enddate = utilitys.getCal2();
					
					utilitys.getCal().setTime(df2.parse(listPetit.get(i).getBlockger2016().getDate_plan_end().trim()));
					startdate_plus = utilitys.getCal();
					
					enddate.set(Calendar.HOUR_OF_DAY, 0);							
					enddate.set(Calendar.MINUTE, 0);
					enddate.set(Calendar.SECOND, 0);
					enddate.set(Calendar.MILLISECOND, 0);
					
					if(!enddate.after(startdate_plus)){
						if(i == 0){listPetit.remove(i); i = 0;}
						else{listPetit.remove(i);	i = i-1;}
						//System.out.println("@@@ 1 "+startdate_plus.getTime()+" - "+enddate.getTime()+" -- "+i);
					}
					
				}
				else if(utilitys.valid(overdueappeal,listPetit.get(i)) == 2){
					utilitys.processDate(listPetit.get(i));
					enddate = utilitys.getCal2();
					
					utilitys.getCal().setTime(df2.parse(listPetit.get(i).getBlockger2016().getDate_plan_end().trim()));
					startdate_plus = utilitys.getCal();
					
					enddate.set(Calendar.HOUR_OF_DAY, 0);							
					enddate.set(Calendar.MINUTE, 0);
					enddate.set(Calendar.SECOND, 0);
					enddate.set(Calendar.MILLISECOND, 0);
					
					if(!enddate.after(startdate_plus)){
						if(i == 0){listPetit.remove(i); i = 0;}
						else{listPetit.remove(i);	i = i-1;}
						//System.out.println("@@@ 2 "+startdate_plus.getTime()+" - "+enddate.getTime()+" -- "+i);
					}
				}
				else if(utilitys.valid(overdueappeal,listPetit.get(i)) == 3){
					utilitys.processDate(listPetit.get(i));
					enddate = utilitys.getCal2();
	
					
					utilitys.getCal().setTime(df2.parse(listPetit.get(i).getBlockger2016().getDate_plan_end().trim()));
					startdate_plus = utilitys.getCal();
					
					enddate.set(Calendar.HOUR_OF_DAY, 0);							
					enddate.set(Calendar.MINUTE, 0);
					enddate.set(Calendar.SECOND, 0);
					enddate.set(Calendar.MILLISECOND, 0);
					
					if(!enddate.after(startdate_plus)){
						if(i == 0){listPetit.remove(i); i = 0;}
						else{listPetit.remove(i);	i = i-1;}
						//System.out.println("@@@ 3 "+startdate_plus.getTime()+" - "+enddate.getTime()+" -- "+i);
					}
				}
				else if(utilitys.valid(overdueappeal,listPetit.get(i)) == 4){
					utilitys.processDate(listPetit.get(i));
					enddate = utilitys.getCal2();
					
					utilitys.getCal().setTime(df2.parse(listPetit.get(i).getBlockger2016().getDate_plan_end().trim()));
					startdate_plus = utilitys.getCal();
					
					enddate.set(Calendar.HOUR_OF_DAY, 0);							
					enddate.set(Calendar.MINUTE, 0);
					enddate.set(Calendar.SECOND, 0);
					enddate.set(Calendar.MILLISECOND, 0);
					
					if(!enddate.after(startdate_plus)){
						if(i == 0){listPetit.remove(i); i = 0;}
						else{listPetit.remove(i);	i = i-1;}
						//System.out.println("@@@ 4 "+startdate_plus.getTime()+" - "+enddate.getTime()+" -- "+i);
					}
				}
				 					
				if(i == listPetit.size()-1 && overdueappeal != null){	listPetit.remove(0); i = i-1;}
			}
    	}	
    	return listPetit;
    }
}
