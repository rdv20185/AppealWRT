package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
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

import pylypiv.tfoms.ftp.FTPDownloadFileDemo;
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

@Controller
public class PetitControllerN {
	
	
	Map<Integer, String> source1 = new HashMap<Integer, String>();
	Map<Integer, String> source2 = new HashMap<Integer, String>();
	Map<Integer, String> source3 = new HashMap<Integer, String>();
	{
		source1.put(1, "ТФОМС");
		source2.put(2, "СМО");
		source3.put(3, "ЕР НСО");
	}
	
	@Autowired
    private PetitService petitService;
	
    
    
	@ModelAttribute
	public ModelMap setupForm(ModelMap map,HttpServletRequest request) {
		
		System.out.println("ModelAttribute "+ request.getRequestURI());
		
		map.put("petit", new Petit());
		List<Petit> pl = new ArrayList<Petit>(); 
    	Petit t = new Petit();
    	pl.add(t);
        map.put("petitList", pl);

    	
		if(getUserName().equals("sasha") ||
				getUserName().equals("mityanina") ||
				getUserName().equals("vasilyeva") ||
				getUserName().equals("smyvin") ||
				getUserName().equals("popova") ||
				getUserName().equals("kuznetsova") ||
				getUserName().equals("eremina") ||
				getUserName().equals("hamitov") ||
				getUserName().equals("filimonova") ||
				getUserName().equals("osipova")) {
			
													map.put("sourceList", source1);
													map.put("conectList", Fields.getConect());
													map.put("presentList", Fields.getPresent());
				if(getUserName().equals("vasilyeva") || getUserName().equals("smyvin"))
				{
					map.put("listassign", Fields.getProperties());
				}else{	map.put("listassign", Fields.getfirsttfoms());	}
		} else {
			if(getUserName().equals("ernso") 
					|| getUserName().equals("call5001")
					|| getUserName().equals("call5002")
					|| getUserName().equals("call5003")
					|| getUserName().equals("callnight5001")
					|| getUserName().equals("callnight5002")
					|| getUserName().equals("callnight5003"))
			{
				if(getUserName().equals("ernso")){map.put("sourceList", source3);}else{map.put("sourceList", source2);}
				map.put("listassign", Fields.getProperties());
				map.put("conectList", Fields.getConectforFL());
				map.put("presentList", Fields.getPresentforFL());
				
			}else{
					map.put("sourceList", source2);
					
					if(getUserName().equals("smo_simaz"))
					{
						map.put("listassign", Fields.getfirstsimaz());	
					}
					if(getUserName().equals("smo_rosno"))
					{
						map.put("listassign", Fields.getfirstrosno());	
					}
					if(getUserName().equals("smo_ingos"))
					{
						map.put("listassign", Fields.getfirstingos());	
					}

					map.put("conectList", Fields.getConect());
					map.put("presentList", Fields.getPresent());
				}
			
		}
		
		
    	
    	map.put("intermedList", Fields.getIntermed());
    	map.put("typeList", Fields.getType());
    	map.put("terList", Fields.getTer());
    	map.put("moList", Fields.getMo());
    	map.put("insurList", Fields.getInsur());
    	map.put("placeList", Fields.getPlace());
    	map.put("validList", Fields.getValid());
    	map.put("hspList", Fields.getHsp());

    	map.put("dateReport", new ReportParams());
    	
		return map;
	}

    @RequestMapping("/index")
    public String listPetits(Map<String, Object> map) {
        return "petit";
    }
   
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPetit(@ModelAttribute("petit") @Valid Petit petit, BindingResult bindingResult,HttpServletRequest request) throws ParseException, InterruptedException {
    	
    	System.out.println("######## "+petit );
    	Thread.sleep(1000000);
    	
    	String para = request.getParameter("submit");
    	
    	/* ловим с клиЕнта нажатую кнопку
    	 * ЕСЛИ с клиента прилетает письменное обращение petit.getConectId() ==2  и дата исходящего пустая getDate_response() то статус = 2(в работе) и date_end = ""
    	 * Если письменное и дата ответа не пустая то статус = 3
    	 */
    	if(para.trim().equals("Завершить")){
    		if(petit.getPresentId() == 2 && petit.getBloutboindletter2016().getDate_response().equals("")){
    			petit.getBlockger2016().setState(2);
    		}else{
    			
    			if(petit.getPresentId() == 2 && !petit.getBloutboindletter2016().getDate_response().equals(""))
    			{
    				petit.getBlockger2016().setState(3);
    				
    	  		  	DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
	        		petit.getBlockger2016().setDate_end(df.parse(petit.getBloutboindletter2016().getDate_response().concat(" 01:00:00.123")));
    			}
    			else{
		    			petit.getBlockger2016().setState(3);
		        		petit.getBlockger2016().setDate_end(new Date());
    			}
    		}
    	}
    	
		return adds(petit, bindingResult,request);
    }

	private String adds(Petit petit, BindingResult bindingResult,HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "petit";
		} else {
			//checkID(petit);
		}
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
	   	
		System.out.println("hjdthrf "+petit.getPresentId()+" "+para.trim()+" "+petit.getBlockger2016().getState());
		
		if(para.trim().equals("Сохранить"))
		{
			//System.out.println("@@!!@@@@@@@!!!!!!!!     "+petit.getUsername());
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
	        		petit.setUsername(getUserName());
	    		}
	    		else{
	    			
	    			if(petit.getPresentId() != 2 && para.trim().equals("Изменить") && petit.getBlockger2016().getState() == 1 ){
	    				
	    				petit.getBlockger2016().setState(2);
		        		petit.setUsername(getUserName());
		    		}
	    			
	    			petit.setUsername(getUserName());
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
	    
	    System.out.println("@@@@@@@@@@@@@@@@@@@@  "+petit);
		petitService.addPetit(petit);
		return "redirect:/index";
	}
    
	private String getUserName() {
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String name = user.getUsername();
		return name;
	}


}
