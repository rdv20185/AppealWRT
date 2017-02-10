package dao;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import domain.Callnight_markerday;
import domain.Petit;

public interface PetitDAO {

    public void addPetit(Petit petit);

    public List<Petit> listPetit(String username);

    public void removePetit(Integer id);
    
    public List<Petit> listSearch(Petit petit, String username, String searchcheckinbound, String overdueappeal) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, Throwable;

	public Petit getPetit(Integer petitId);
	
	public List<Date> getMaxDate();
	
	public void close(Integer petitId);
	
	public List<domain.Calendar> getCeleb(Date date) throws ParseException;
	
	public void updateLastDate(Callnight_markerday  cm);
	
}