package dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import domain.Callnight_markerday;
import domain.CdrQuery;
import domain.Petit;
import domain.Subtype;

public interface PetitDAO {

    public void addPetit(Petit petit);

    public List<Petit> listPetit(String username, Set<String> role );

    public void removePetit(Integer id);
    

	public Petit getPetit(Integer petitId);
	
	public List<Date> getMaxDate();
	
	public void close(Integer petitId);
	
	public List<domain.Calendar> getCeleb(Date date) throws ParseException;
	
	public void updateLastDate(Callnight_markerday  cm);
	
	/**
	 * Метод обновляет плановую дату ответа
	 * @param id
	 * @param plan_date - пдановая дата ответа
	 */
	public void update_PlaneDateField(String id, String plan_date);
	
	public void addCdrQuery(CdrQuery model);
	
	public List<Petit> listSearch(Petit petit, String username, String searchcheckinbound, String overdueappeal,Set<String> role) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	
	public void deleteByIdSubtype(Integer id);
	
}	