package util;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Util {
	
	public static long daysBetween(Calendar startDate, Calendar endDate) {  
		  Calendar date = (Calendar) startDate.clone();  
		  long daysBetween = 0;  
		  while (date.before(endDate)) {  
		    date.add(Calendar.DAY_OF_MONTH, 1);  
		    daysBetween++;  
		  }  
		  return daysBetween;  
		} 
	
	
	public static String importSQL(InputStream in) throws SQLException
	{
	    Scanner s = new Scanner(in);
	    s.useDelimiter("(;(\r)?\n)|(--\n)");
	    
	    String query = "";
	    while (s.hasNext())
        {
	    	//if (line.startsWith("/*!") && line.endsWith("*/"))
            query = query + s.next();
        }
	   
	    return query;
	}
	
	
}
