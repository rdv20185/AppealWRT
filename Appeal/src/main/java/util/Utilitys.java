package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	public int valid(String overdueappeal,Petit petit){
		
		if(overdueappeal != null && petit.getBlockger2016().getDate_end() != null && petit.getBloutboindletter2016().getDate_between().equals("")){
			return 1;
		}
		if(overdueappeal != null && petit.getBlockger2016().getDate_end() != null && petit.getBloutboindletter2016().getDate_between().length() > 1){
			return 2;
		}
		if(overdueappeal != null && petit.getBlockger2016().getDate_end() == null && petit.getBloutboindletter2016().getDate_between().length() > 1 ){
			return 3;
		}
		if(overdueappeal != null && petit.getBlockger2016().getDate_end() == null && petit.getBloutboindletter2016().getDate_between().equals("") ){
			return 4;
		}
		
		
		return 0;
	}
	
	/**
	 * Метод преобразует в объект Calendar строковую данные и данные объекта Date
	 * @param petit Объект с необходимыми датами getDateInput() и getDate_end() 
	 * Если getDate_end() == null (в случае если обращение является еще в работе)
	 * присваиваем текущюю дату, т.е. рассматриваем на текущий день.
	 * @throws ParseException
	 */
	public void processDate(Petit petit) throws ParseException{
		
		getCal().setTime(df.parse(petit.getDateInput().substring(0, 11).trim()));
		
		if(petit.getBlockger2016().getDate_end() == null){
			getCal2().setTime(new Date());
		}else{
			getCal2().setTime(petit.getBlockger2016().getDate_end());
		}
		
	}
	
	/**
	 * Расчет дней между датами. Примечание: не учитывает праздничне дни.
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
	
	/**
	 * Метод смещает дату на определеное количиство дней (count_days,tehnick_count_date).
	 * count_days - дата, непосредственно на которую смещаем дату
	 * tehnick_count_date - техническая,предназначена для смещения на случай колибровки расчета
	 * дата. То есть с логике разделена дата увеличения и дата технического увеличения (для колибровки, пристредки, отладки) с целью
	 * чтобы было понятно какое количество дней для чего используется.
	 * 
	 * @param startDate
	 * @param count_days
	 * @param tehnick_count_date
	 * @return
	 */
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
