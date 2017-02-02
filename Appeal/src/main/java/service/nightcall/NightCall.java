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

import org.apache.log4j.Logger;
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

	private static final Logger logger = Logger.getLogger(NightCall.class);
	
	@Autowired
    private PetitService petitService;
	@Autowired
	private ServletContext servletContext;
	
		
	  public void printMessage() throws ParseException {
		
		  if(logger.isInfoEnabled()){ logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$          начало обработки   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");}
			
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  
		  // convert date to yyyy-dd-mm (across string)
		  List<Date> maxDate_ = petitService.getMaxDate();
		  String maxDate_str = maxDate_.get(0).toString();
		  Date maxDate = formatter.parse(maxDate_str);
		  
		  // convert date to yyyy-dd-mm (across string)
		  String format_str = formatter.format(new Date());
		  Date format_date_today = formatter.parse(format_str);
		  
		  	if(maxDate.compareTo(format_date_today)>0){
		  		if(logger.isInfoEnabled()){ logger.info("Последняя дата обработки: "+ maxDate+"  Сегодня: "+format_date_today);}
  			}else if(maxDate.compareTo(format_date_today)<0){
  				if(logger.isInfoEnabled()){ logger.info("Последняя дата обработки: "+ maxDate+"  Сегодня: "+format_date_today);}
  				
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
    	  		          if(logger.isInfoEnabled()){ logger.info("Период забора праздничный день: c "+ start_day.getTime()+" по "+cal.getTime());}
    	  		          block_ftp.startFtp(start_day,cal);
    	  		          // заносим в базу отработанную дату
    	  		          petitService.updateLastDate(cal);
    	  		          maxDate  = cal.getTime();
  					}
  					else if((start_day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (start_day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) )
  	  		        {
  	  		          // start_day 9_00 - date_increment до 9_00
  	  		          
  	  		          cal.add(Calendar.DATE, 1);
  	  		          
  	  		          start_day.set(Calendar.HOUR, 9);
  	  		          cal.set(Calendar.HOUR, 9);
  	  		          if(logger.isInfoEnabled()){ logger.info("Период забора выходной день: c "+ start_day.getTime()+" по "+cal.getTime());}
  	  		          block_ftp.startFtp(start_day,cal);
	  		          // заносим в базу отработанную дату
  	  		          petitService.updateLastDate(cal);
  	  		          maxDate  = cal.getTime();
  	  		        }
  	  		        // и не праздничный добавить
  	  		        else if(start_day.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
  	  		        	
  	  	  		        // start_day 17_00 - date_increment до 9_00
  	  		        	
  	  		        	cal.add(Calendar.DATE, 1);
  	  		        	
  	  		        	start_day.set(Calendar.HOUR, 17);
  	  		        	cal.set(Calendar.HOUR, 9);
  	  		        	if(logger.isInfoEnabled()){ logger.info("Период забора пятницы: c "+ start_day.getTime()+" по "+cal.getTime());}
  	  		        	block_ftp.startFtp(start_day,cal);
  	  		        	// 	заносим в базу отработанную дату
  	  		        	petitService.updateLastDate(cal);
  	  		        	maxDate  = cal.getTime();
  	  		        	
  	  		        }
  	  		        else{
  	  		        	
  	  		        	// 	start_day 18_00 - date_increment до 9_00
  	  		        	cal.add(Calendar.DATE, 1);
  	  		        	
  	  		        	start_day.set(Calendar.HOUR, 18);
  	  		        	cal.set(Calendar.HOUR, 9);
  	  		        	if(logger.isInfoEnabled()){ logger.info("Обычный период забора : c "+ start_day.getTime()+" по "+cal.getTime());}
  	  		        	block_ftp.startFtp(start_day,cal);
  	  		        	// заносим в базу отработанную дату
  	  		        	petitService.updateLastDate(cal);
  	  		        	maxDate  = cal.getTime();
  	  		        	
  	  		        }
  	  		        
  				}
  				
  			}else if(maxDate.compareTo(format_date_today)==0){
  				if(logger.isInfoEnabled()){ logger.info("Последняя дата обработки равна сегодняшней : последняя ");}
  			}
		  
		  	if(logger.isInfoEnabled()){ logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$          конец обработки   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");}
		  	
			 // 	nightcallsprocess();
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
