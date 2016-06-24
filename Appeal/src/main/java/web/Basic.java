package web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.BlockGER2016;
import domain.Petit;
import domain.ReportParams;
import res.Fields;
import res.TransferFiles;
import service.PetitService;

@Controller
public class Basic {
	
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

		System.out.println("INFO "+request.getRequestURI());
		nightcallsprocess(request);
		
    	map.put("petit", new Petit());
    	
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
    	
    	List<Petit> pl = petitService.listPetit(getUserName()); 
    	for(Petit pt : pl)
    	{
    		if(pt.getDateInput() !=null)
    		pt.setDateInput(pt.getDateInput().substring(8, 10) + "." + pt.getDateInput().substring(5, 7) + "." + pt.getDateInput().substring(0, 4));
    	}
        map.put("petitList", pl);
    	
        return "petit";
    }
    
    private String getUserName() {
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String name = user.getUsername();
		return name;
	}
    
    private void nightcallsprocess(HttpServletRequest request){
    	String path = request.getServletContext().getRealPath("/")+"night_calls_working";
    	String path_worked = request.getServletContext().getRealPath("/")+"night_calls_worked";
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
	    				petit.setDateInput(ff);
	    				petit.setTel(tel);
	    				petit.setUsername("auto");
	    				BlockGER2016 blo = new BlockGER2016();
	    				blo.setRegname("auto");
	    				blo.setRegsource_id(2);
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
    	System.out.println("WWWWWWW "+path);
    	
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
	
}
