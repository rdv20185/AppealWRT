package service.nightcall;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import domain.BlockGER2016;
import domain.Petit;
import res.TransferFiles;
import service.PetitService;

@Component("myBean")
public class NightCall {

	@Autowired
    private PetitService petitService;
	
	Date now; // to display current time

	  public void printMessage() {
		 // 	nightcallsprocess();
	       // System.out.println("I am called by Spring scheduler");
	    }
	
 private void nightcallsprocess(){
	 	HttpServletRequest request = null;
    	String path = request.getServletContext().getRealPath("/")+"night_calls_working";
    	String path_worked = request.getServletContext().getRealPath("/")+"night_calls_worked";
    	System.out.println("NTCN "+ path);
    	System.out.println("NTCN2 "+ path_worked);
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
