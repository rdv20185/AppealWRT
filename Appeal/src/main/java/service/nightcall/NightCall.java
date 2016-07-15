package service.nightcall;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import domain.BlockGER2016;
import domain.Petit;
import ftp.FTPDownloadFileDemo;
import res.TransferFiles;
import service.PetitService;

@Component("myBean")
public class NightCall {

	@Autowired
    private PetitService petitService;
	@Autowired
	private ServletContext servletContext;
	
	Date now; // to display current time
		
	  public void printMessage() throws ParseException {
		 // 	nightcallsprocess();
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  
		  // convert date to yyyy-dd-mm (across string)
		  List<Date> maxDate_ = petitService.getMaxDate();
		  String maxDate_str = maxDate_.get(0).toString();
		  Date maxDate = formatter.parse(maxDate_str);
		  
		  // convert date to yyyy-dd-mm (across string)
		  String format_str = formatter.format(new Date());
		  Date format_date_today = formatter.parse(format_str);
		  
		  	if(maxDate.compareTo(format_date_today)>0){
			  System.out.println("Date1 is after Date2"+" - "+ maxDate+" - "+format_date_today);
  			}else if(maxDate.compareTo(format_date_today)<0){
  				System.out.println("Date1 is before Date2"+" - "+ maxDate+" - "+format_date_today);
  				
  				Calendar cal = Calendar.getInstance();
  				Calendar cal2 = Calendar.getInstance();
  				FTPDownloadFileDemo block_ftp = new FTPDownloadFileDemo();
  				while(maxDate.compareTo(format_date_today) <0){
  					
  					cal.setTime(maxDate);
  					
  					cal2.setTime(maxDate);
  					Calendar start_day = cal2;
  					start_day.set(Calendar.HOUR, 0);

  	  		        // check friday
  					if(petitService.isCeleb(start_day.getTime())){
  						
  						  cal.add(Calendar.DATE, 1);
    	  		          
    	  		          start_day.set(Calendar.HOUR, 9);
    	  		          cal.set(Calendar.HOUR, 9);
    	  		          System.out.println("Период забора проздничный "+ start_day.getTime()+" $$$$$$$$$$$$$$$$$$$$$$$ "+cal.getTime());
    	  		          block_ftp.startFtp(start_day,cal);
    	  		          maxDate  = cal.getTime();
  					}
  					else if((start_day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (start_day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) )
  	  		        {
  	  		          // start_day 9_00 - date_increment до 9_00
  	  		          
  	  		          cal.add(Calendar.DATE, 1);
  	  		          
  	  		          start_day.set(Calendar.HOUR, 9);
  	  		          cal.set(Calendar.HOUR, 9);
  	  		          System.out.println("Период забора выходные "+ start_day.getTime()+" $$$$$$$$$$$$$$$$$$$$$$$ "+cal.getTime());
  	  		          block_ftp.startFtp(start_day,cal);
  	  		          maxDate  = cal.getTime();
  	  		        }
  	  		        // и не праздничный добавить
  	  		        else if(start_day.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
  	  		        	
  	  	  		        // start_day 17_00 - date_increment до 9_00
  	  		        	
  	  		        	cal.add(Calendar.DATE, 1);
  	  		        	
  	  		        	start_day.set(Calendar.HOUR, 17);
  	  		        	cal.set(Calendar.HOUR, 9);
  	  		        	System.out.println("Период забора пятница "+ start_day.getTime()+" $$$$$$$$$$$$$$$$$$$$$$$ "+cal.getTime());
  	  		        	block_ftp.startFtp(start_day,cal);
  	  		        	maxDate  = cal.getTime();
  	  		        	
  	  		        }
  	  		        else{
  	  		        	
  	  		        	// 	start_day 18_00 - date_increment до 9_00
  	  		        	cal.add(Calendar.DATE, 1);
  	  		        	
  	  		        	start_day.set(Calendar.HOUR, 18);
  	  		        	cal.set(Calendar.HOUR, 9);
  	  		        	System.out.println("Обычный забор "+start_day.getTime()+" $$$$$$$$$$$$$$$$$$$$$$$ "+cal.getTime());
  	  		        	block_ftp.startFtp(start_day,cal);
  	  		        	maxDate  = cal.getTime();
  	  		        	
  	  		        }
  	  		        
  				}
  				
  			}else if(maxDate.compareTo(format_date_today)==0){
  				System.out.println("Date1 is equal to Date2"+" - "+ maxDate+" - "+format_date_today);
  			}
		  
	       System.out.println("I am called by Spring scheduler " +" ## "+servletContext.getRealPath("/"));
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
