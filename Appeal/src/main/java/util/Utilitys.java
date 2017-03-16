package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import domain.Petit;


/**
 * @author pylypiv.sergey
 * 14.03.2017
 * Класс утилит. Вспомогательный класс который разделяет выполнение логики.
 * 
 */
@Component
public class Utilitys {
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
	private Calendar cal  = Calendar.getInstance();
	private Calendar cal2  = Calendar.getInstance();

	public Utilitys(){}
	
	
	/**
	 * @param overdueappeal  Флаг просроченного сообщения (вкл/выкл) То есть выбираем или нет просроченные обращения.
	 * @param petit  Объект обращения
	 * @return Возвращает true если срабатывает одно из условий алгоритма "просроченного обращения"
	 * getDate_end() дата окончательного ответа
	 * getDate_between() дата промежуточного ответа  
	 */
	public boolean valid(String overdueappeal,Petit petit){
		
		if(overdueappeal != null && petit.getBlockger2016().getDate_end() != null && petit.getBloutboindletter2016().getDate_between().equals("")){
			return true;
		}
		if(overdueappeal != null && petit.getBlockger2016().getDate_end() != null && petit.getBloutboindletter2016().getDate_between().length() > 1){
			return true;
		}
		if(overdueappeal != null && petit.getBlockger2016().getDate_end() == null && petit.getBloutboindletter2016().getDate_between().length() > 1 ){
			return true;
		}
		if(overdueappeal != null && petit.getBlockger2016().getDate_end() == null && petit.getBloutboindletter2016().getDate_between().equals("") ){
			return true;
		}
		
		
		return false;
	}
	
	/**
	 * Метод преобразует в объект Calendar строковую данные и данные объекта Date
	 * @param petit Объект с необходимыми датами getDateInput() и getDate_end() 
	 * @throws ParseException
	 */
	public void processDate(Petit petit) throws ParseException{
		
		getCal().setTime(df.parse(petit.getDateInput().substring(0, 11).trim()));
		getCal2().setTime(petit.getBlockger2016().getDate_end());
		
	}
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public long daysBetween(Calendar startDate, Calendar endDate) {  
		  Calendar date = (Calendar) startDate.clone();  
		  long daysBetween = 0;  
		  while (date.before(endDate)) {  
		    date.add(Calendar.DAY_OF_MONTH, 1);  
		    daysBetween++;  
		  }  
		  return daysBetween;  
	} 
	
	public Calendar daysPlus(Calendar startDate,int count_days,int tehnick_count_date){
		startDate.add(Calendar.DATE, tehnick_count_date);
		startDate.add(Calendar.DATE, count_days);
		return startDate;		
	}


	public DateFormat getDf() {
		return df;
	}


	public void setDf(DateFormat df) {
		this.df = df;
	}


	public DateFormat getDf2() {
		return df2;
	}


	public void setDf2(DateFormat df2) {
		this.df2 = df2;
	}


	public Calendar getCal() {
		return cal;
	}


	public void setCal(Calendar cal) {
		this.cal = cal;
	}


	public Calendar getCal2() {
		return cal2;
	}


	public void setCal2(Calendar cal2) {
		this.cal2 = cal2;
	}


		
	
}
