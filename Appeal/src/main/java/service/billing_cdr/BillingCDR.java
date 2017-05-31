package service.billing_cdr;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import ftp.Option;
import ftp.datasource.Ftp_datasource;
import service.PetitService;

@Component("billingcdr")
public class BillingCDR {

	private static final Logger logger = Logger.getLogger(BillingCDR.class);
	
	@Autowired
    private PetitService petitService;
	@Autowired
    private Ftp_datasource ftp_datasource;
	
		
  /**
   * Метод парсит cdr файл  с ftp сервера телефонной станции и передает на слой сервиса.
   *  
   * @throws ParseException
   * @throws IOException
   */
public void run() throws ParseException, IOException {
	  
	    if(logger.isInfoEnabled()){ logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$    start process cdrimport   $$$$$$$$$$$$$$$$$$$$$$");}
	    
	    FTPClient ftpclient   = null;
	    String directoryFrom  = null;
	    
	    Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    // 
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    int day = cal.get(Calendar.DAY_OF_MONTH);

	    String []mas = null;
	    Map<String, String> treeMap = new TreeMap<String, String>();
	    
	    
	  	ftpclient= ftp_datasource.getConnection();
	  	directoryFrom = Option.getDirectory("dirfromcdr");
	  	String apendix = "cdr"+year+"0"+(month+1)+day+"/";
	  	
	  	if(logger.isInfoEnabled()){ logger.info("-> process from directory "+directoryFrom+apendix);}
	  	
        FTPFile[] files = ftpclient.listFiles(directoryFrom+apendix);
        try{
	        for(FTPFile file : files){
	        	
	        	String details = file.getName();
                details += "\t\t" + file.getSize();
                details += "\t\t" + file.getTimestamp().getTime().toString();
                
                if(logger.isInfoEnabled()){ logger.info("-> file name, size, date change "+details);}
                
                File fileFromFtp = new File(file.getName());
                fileFromFtp.createNewFile();
                
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileFromFtp));
                ftpclient.retrieveFile(directoryFrom+apendix+file.getName(), outputStream);
                outputStream.close();
                Reader reader = new InputStreamReader(new FileInputStream(fileFromFtp));
                BufferedReader br = new BufferedReader(reader);
                
                try {
                    String line = null;
                    while ((line = br.readLine()) != null) {
                    	
                        mas = line.split(";");
                        if(mas.length > 1){
                        	treeMap.put(mas[1], line);
                        	if(logger.isInfoEnabled()){ logger.info(line);}
                        }
                    }
                } finally {
                    br.close();
                }
	        }
    	}finally{
    		ftp_datasource.disconnect_ftp(ftpclient);
    	}   
	        treeMap.size();
	        if(logger.isInfoEnabled()){ logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$ End process cdr $$$$$$$$$$$$$$$$$$$$$$$$$$ ");}
	        
	        
	    petitService.createcdr(treeMap);
  }
	 
}
