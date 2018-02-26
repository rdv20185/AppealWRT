package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import domain.BlockGER2016;
import domain.IRPLIST;
import domain.IRPLIST.IRP;
import domain.Petit;
import ftp.Option;


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
	 * Метод возвращает флаг исхода одного из условий для вычисления даты просроченного обращения.
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
	 * Метод возвращает флаг исхода одного из условий для вычисления даты просроченного обращения.
	 * @param petit Объект обращения
	 * @return Возвращает true если срабатывает одно из условий алгоритма "просроченного обращения"
	 */
	public int valid(Petit petit){
		
		if(petit.getBlockger2016().getDate_end() != null && petit.getBloutboindletter2016().getDate_between().equals("")){
			return 1;
		}
		if(petit.getBlockger2016().getDate_end() != null && petit.getBloutboindletter2016().getDate_between().length() > 1){
			return 2;
		}
		if(petit.getBlockger2016().getDate_end() == null && petit.getBloutboindletter2016().getDate_between().length() > 1 ){
			return 3;
		}
		if(petit.getBlockger2016().getDate_end() == null && petit.getBloutboindletter2016().getDate_between().equals("") ){
			return 4;
		}
		
		
		return 0;
	}
	
	/**
	 * Метод преобразует в объект Calendar строкову данные и данные объекта Date
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


	/**
	 * Метод разархивации 
	 * @param bytes
	 * @return массив файлов из архива
	 * @throws IOException 
	 */
	public List<File> extractArchive(File file) throws IOException {
		
		String pathStoreExractFiles = Option.getDirectory("directoryExtractFiles","directories.properties");
		
		byte[] buffer = new byte[1024];
		
		ZipInputStream zis =new ZipInputStream(new FileInputStream(file));
		ZipEntry ze = zis.getNextEntry();
		List<File> ls_file = new ArrayList<File>();
		
		
		while(ze!=null){

	    	   String fileName = ze.getName();
	           File newFile = new File(pathStoreExractFiles + File.separator + fileName);

	           System.out.println("file unzip : "+ newFile.getAbsoluteFile());

	            //create all non exists folders
	            //else you will hit FileNotFoundException for compressed folder
	           //if (!newFile.exists())
	        	   //new File(newFile.getParent()).mkdirs();
	           //if(newFile.isFile()){

		            FileOutputStream fos = new FileOutputStream(newFile);
	
		            int len;
		            while ((len = zis.read(buffer)) > 0) {
		            	fos.write(buffer, 0, len);
		            }
	
		            fos.close();
		            ls_file.add(newFile);
	           //}
	            ze = zis.getNextEntry();
	    	}

		zis.closeEntry();
		zis.close();
		

		return ls_file;
	}


	/**
	 * @param xml_file
	 * @return возвращает parsed xml в объект 
	 * @throws JAXBException 
	 * @throws FileNotFoundException 
	 */
	public List<IRPLIST> unmarshal(List<File> xml_file) throws FileNotFoundException, JAXBException {
		
		JAXBContext context = JAXBContext.newInstance(IRPLIST.class);
        Unmarshaller um = context.createUnmarshaller();
        IRPLIST irplist = null;
        List<IRPLIST> ls = new ArrayList<IRPLIST>();
		
		for(File f : xml_file){
			
	        irplist = (IRPLIST) um.unmarshal(new FileReader(f.getAbsolutePath()));
	        ls.add(irplist);
	        
	        	System.out.println(ls);	
		}
		
		return ls;
	}


	/**
	 * Метод "перекодировки" из xml объекта в entity объект
	 * @param model
	 * @return коллекцию объектов
	 */
	public List<Petit> transformToEntity(List<IRPLIST> model) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		List<Petit> list_p = new ArrayList<Petit>();
		Petit p = null;
		
		for(IRPLIST m : model){
			for(IRP irp : m.getIRP()){
				
				p = new Petit();
				p.setBlockger2016(new BlockGER2016());
				//p.setDateInput(irp.getDATECREATE());
				
				p.setDateInput(sdf.format(new Date()));
				p.setConectId(1); // 1 - телефон
				p.setPresentId(1);
				p.setUsername("test");
				p.setSurname(irp.getZSV().getZI());
				p.setName(irp.getZSV().getZF());
				p.setPatrony(irp.getZSV().getZO());
				//irp.getZSV().getZDR();
				p.setPolicy(irp.getZSV().getZENP());
				//irp.getZSV().getZSMO();
				//irp.getZSV().getZDOCTYPE();
				//irp.getZSV().getZDOCSER();
				//irp.getZSV().getZDOCNUM();
				p.setTel(irp.getZSV().getPHONE());
				//irp.getZSV().getEMAIL();
				// территория ответчик ???
				
				// по 79 1- консультация; у нас 3
				// по 79 2- жалоба		  у нас 1
				// по 79 3- предложение   у нас 4
				// по 79 4- заявление     у нас 2
				// по 79 5- вопросы не по омс у нас НЕТ
				if (irp.getIRPTYPE() == 1) p.setTypeId(3);
				if (irp.getIRPTYPE() == 2) p.setTypeId(1);
				if (irp.getIRPTYPE() == 3) p.setTypeId(4);
				if (irp.getIRPTYPE() == 4) p.setTypeId(2);
				if (irp.getIRPTYPE() == 5) p.setTypeId(3);
				
				//1.1.	Об обеспечении полисами ОМС
				if(irp.getTHEME().equals("1.1.")){
					if(p.getTypeId() == 1) p.setCauseId(1);
					if(p.getTypeId() == 3) p.setCauseId(22);
				}
				// 1.1.2.	Об обеспечении полисами ОМС иностранных граждан, беженцев
				if(irp.getTHEME().equals("1.1.2.")){
					//todo: ......
				}
				// 1.2.	О выборе МО в сфере ОМС
				
				/*
				
				1.3.	О выборе врача
				1.4.	О выборе и замене СМО
				1.5.	Об идентификации в качестве застрахованного лица
				1.6.	Об организации работы МО
				1.7.	О санитарно-гигиеническом состоянии МО
				1.8.	О материально-техническом обеспечении МО
				1.9.	Об этике и деонтологии медицинских работников
				1.10.	О КМП
				1.11.	О лекарственном обеспечении при оказании медицинской помощи
				1.12.	Об отказе в оказании медицинской помощи по программам ОМС
				1.13.	О получении медицинской помощи по базовой программе ОМС вне территории страхования
				1.14.	О взимании денежных средств за медицинскую помощь по программам ОМС, в том числе:
				1.14.1.	О видах, качестве и условиях предоставления медицинской помощи по программам ОМС
				1.15.	О платных медицинских услугах, оказываемых в МО
				1.16.	О неисполнении СМО обязанностей по договору
				1.17.	О неправомерном распространении персональных данных
				1.18.	О выделении средств для оплаты МП в рамках ТПГГ оказания бесплатной медицинской помощи
				1.19.	О вопросах, не относящихся к сфере ОМС
				1.20.	Другие
				1.21.	Предложения
			    */
				
				// адрес
				// cause_id
				// cause_note

				// rectif1_id 
				// satisf
				// tel
				// ter_answer_id
				// ter_id
				//type_id
				// valid_id
				
				p.getBlockger2016().setRegname("test");
				p.getBlockger2016().setDate_end(new Date());
				// regsource_id
				
				p.getBlockger2016().setState(4);
				p.getBlockger2016().setPetit(p);
				
				p.setBloutboindletter2016(null);
				
				list_p.add(p);
			}
		}
		
		
		return list_p;
	}


		
	
}
