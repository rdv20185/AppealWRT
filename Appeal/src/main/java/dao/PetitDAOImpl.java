package dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domain.Callnight_markerday;
import domain.CdrQuery;
import domain.Outboundmany;
import domain.Petit;

@Repository
public class PetitDAOImpl implements PetitDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPetit(Petit petit) {
    	
        sessionFactory.getCurrentSession().saveOrUpdate(petit);
        
    }
    
    @Transactional
	public void addCdrQuery(CdrQuery model) {
	    	
	        sessionFactory.getCurrentSession().saveOrUpdate(model);
	        
    }
    
    /* (non-Javadoc)
     * @see dao.PetitDAO#update_PlaneDateField(java.lang.String, java.lang.String)
     */
    @Override
    public void update_PlaneDateField(String id, String plan_date) {
    	Integer id_i = Integer.valueOf(id);
    	Query query = null;
    	query = sessionFactory.getCurrentSession().createQuery("update BlockGER2016 t2 set t2.date_plan_end=:plan_date wherem t2.idblockger2016=:id_i");
        query.setParameter("id_i", id_i);
        query.setParameter("plan_date", plan_date);
        query.executeUpdate();
    }
    

    @SuppressWarnings("unchecked")
    public List<Petit> listPetit(String username) {
    	Query query = null;
    	
    	if(username.equals("ernso"))
    	{
    		query = sessionFactory.getCurrentSession().createQuery(
        			"select t from Petit t, BlockGER2016 t2 where (t.username = :username or t.username='"+"ТФОМС"+"' or t.username='"+"СИМАЗ"+"' or t.username='"+"РОСНО"+"' or t.username='"+"ИНГОССТРАХ"+"')and t.id=t2.idblockger2016 and t2.regname='"+"ernso"+"' order by t.id desc");
            query.setParameter("username", username);
            query.setMaxResults(100);
    	}
    	
    	if(username.equals("callnight5001") || username.equals("callnight5002") || username.equals("callnight5003")){
    		query = sessionFactory.getCurrentSession().createQuery(
        			"select t from Petit t, BlockGER2016 t2  where (t.username = :username or t.username = '"+"callnight5003"+"' or t.username = '"+"ТФОМС"+"' or t.username = '"+"callnight5001"+"' or t.username = '"+"callnight5002"+"' or t.username='"+"auto"+"'   or t.username='"+"СИМАЗ"+"' or t.username='"+"РОСНО"+"' or t.username='"+"ИНГОССТРАХ"+"') and"
        					+ " t.id=t2.idblockger2016 and (t2.regname='"+"auto"+"' or t2.regname='"+"callnight5001"+"' or t2.regname='"+"callnight5002"+"' or t2.regname='"+"callnight5003"+"')  order by t.id desc");
            query.setParameter("username", username);
            query.setMaxResults(100);
    	}
    	
    	if(username.equals("call5001"))
    	{
    		query = sessionFactory.getCurrentSession().createQuery(
        			"select t from Petit t, BlockGER2016 t2  where (t.username = :username or t.username='"+"ТФОМС"+"' or t.username='"+"СИМАЗ"+"' or t.username='"+"РОСНО"+"' or t.username='"+"ИНГОССТРАХ"+"')and t.id=t2.idblockger2016 and t2.regname='"+"call5001"+"'   order by t.id desc");
            query.setParameter("username", username);
            query.setMaxResults(100);
    	}
    	
    	if(username.equals("call5002"))
    	    	{
    	    		query = sessionFactory.getCurrentSession().createQuery(
    	        			"select t from Petit t, BlockGER2016 t2  where (t.username = :username or t.username='"+"ТФОМС"+"' or t.username='"+"СИМАЗ"+"' or t.username='"+"РОСНО"+"' or t.username='"+"ИНГОССТРАХ"+"')and t.id=t2.idblockger2016 and t2.regname='"+"call5002"+"'  order by t.id desc");
    	            query.setParameter("username", username);
    	            query.setMaxResults(100);
    	    	}
    	
    	if(username.equals("call5003"))
 	    	{
 	    		query = sessionFactory.getCurrentSession().createQuery(
 	        			"select t from Petit t, BlockGER2016 t2  where (t.username = :username or t.username='"+"ТФОМС"+"' or t.username='"+"СИМАЗ"+"' or t.username='"+"РОСНО"+"' or t.username='"+"ИНГОССТРАХ"+"')and t.id=t2.idblockger2016 and t2.regname='"+"call5003"+"'   order by t.id desc");
 	            query.setParameter("username", username);
 	           query.setMaxResults(100);
 	    	}
    	
		if(username.equals("hamitov"))
    	{
			query = sessionFactory.getCurrentSession().createQuery(
			"select r from Petit r where (r.username = :username or r.username='"+"ТФОМС"+"')  order by id desc");
			query.setParameter("username", username);
			query.setMaxResults(100);
    	}
		
		if(username.equals("mityanina") || username.equals("eremina"))
    	{
			query = sessionFactory.getCurrentSession().createQuery(
			"select r from Petit r where  r.blockger2016.state < 3 and (r.username = :username or r.username='"+"ТФОМС"+"')  order by r.blockger2016.state asc,to_date(r.blockger2016.date_plan_end,'dd.MM.yyyy') asc, id desc");
			query.setParameter("username", username);
			query.setMaxResults(50);
			List<Petit> ls = new ArrayList<Petit>(100);
			ls.addAll(query.list());
			
			query = sessionFactory.getCurrentSession().createQuery("select r from Petit r where r.blockger2016.state >= 3 and (r.username = :username or r.username='"+"ТФОМС"+"')  order by to_date(r.blockger2016.date_end,'dd.MM.yyyy') desc");
			query.setParameter("username", username);
			query.setMaxResults(50);
			ls.addAll(query.list());
			
			return ls;
			
    	}
    	
    	if(username.equals("kuznetsova"))
    	{
			query = sessionFactory.getCurrentSession().createQuery(
			"select t from Petit t, BlockGER2016 t2 where t2.regname = :username and t.id=t2.idblockger2016 order by id desc");
			//"from Petit order by id desc");
			//query.setMaxResults(1000);
			query.setParameter("username", username);
			query.setMaxResults(100);
    	}
		
		
		
		if(username.contains("smo_ingos"))
    	{
			query = sessionFactory.getCurrentSession().createQuery(
			"from Petit where (username like '%'||'"+"smo_ingos"+"'||'%' or username='"+"ИНГОССТРАХ"+"') order by id desc");
            //query.setParameter("username", username);
            query.setMaxResults(100);
    	}
		
		if(username.contains("smo_rosno"))
    	{
			query = sessionFactory.getCurrentSession().createQuery(
			"from Petit where (username like '%'||'"+"smo_rosno"+"'||'%' or username='"+"РОСНО"+"')  order by  id desc");
          //  query.setParameter("username", username);
			query.setMaxResults(100);
    	}
		
		if(username.equals("smo_simaz"))
    	{
			query = sessionFactory.getCurrentSession().createQuery(
			"from Petit where (username = :username or username='"+"СИМАЗ"+"') order by  id desc");
            query.setParameter("username", username);
            query.setMaxResults(100);
    	}
		
		if(username.equals("vasilyeva") || username.equals("smyvin"))
    	{
			query = sessionFactory.getCurrentSession().createQuery("from Petit order by id desc");
			query.setMaxResults(100);
    	}
        return query.list();
    }

    public void removePetit(Integer id) {
    	Petit petit = (Petit) sessionFactory.getCurrentSession().load(
    			Petit.class, id);
        if (null != petit) {
            sessionFactory.getCurrentSession().delete(petit);
        }
    }
    
    public void removeOutboundmany(Integer id) {
        Query query = null;
		query = sessionFactory.getCurrentSession().createQuery("delete from Outboundmany t where (t.idlettermany = :id");
        query.setParameter("id", id);
        
        query.executeUpdate();
    	
    }
    
    public void updateLastDate(Callnight_markerday  cm) {
    	 sessionFactory.getCurrentSession().saveOrUpdate(cm);
    }
    
    @SuppressWarnings("unchecked")
	public List<Petit> listSearch(Petit petit, String username, String searchcheckinbound, String overdueappeal) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	
    	Criteria criteria =  sessionFactory.getCurrentSession().createCriteria(Petit.class,"p");
    	criteria.createAlias("p.blockger2016", "b");
    	
    	Method[] methods = Petit.class.getMethods();
    	String fieldName,fieldName2 ; 
    	for (Method method : methods) {
    		if (isGetter(method)) {
        		if(method.invoke(petit) != null &&
        		   !"".equals(method.invoke(petit)) &&
        		   !"getClass".equals(method.getName()) &&
        		   !"0".equals(method.invoke(petit).toString()) &&
        		   !"getDateBegin".equals(method.getName()) &&
        		   !"getDateEnd".equals(method.getName())) {
        			
        			
        			fieldName = method.getName().replaceAll("get", "").substring(0, 1);
        			fieldName = fieldName.toLowerCase().concat(method.getName().replaceAll("get", "").substring(1));
        			
        			
        			if(method.getName().equals("getBlockger2016") && method.getReturnType().getMethods().length != 0){
	        			for(Method method_s :method.getReturnType().getMethods()){
	        				if (isGetter(method_s) && "getClaim_inshur".equals(method_s.getName()) &&  !method_s.invoke(petit.getBlockger2016()).equals("")) {
	        					fieldName2 = method_s.getName().replaceAll("get", "").substring(0, 1);
	        					fieldName2 = fieldName2.toLowerCase().concat(method_s.getName().replaceAll("get", "").substring(1));
	        					//System.out.println("!!2 "+fieldName2+" --- "+ method_s.invoke(petit.getBlockger2016()));
	        					criteria.add(Restrictions.eq("b."+fieldName2, method_s.invoke(petit.getBlockger2016())));
	        				}	
	        			}
        			}else{ /*System.out.println("!! "+fieldName+" --- "+ method.invoke(petit));*/ criteria.add(Restrictions.eq("p."+fieldName, method.invoke(petit)));}
        		}
   		   	}
   	   	}

    	Pattern p = Pattern.compile("(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}");
    	DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
    	
    	/*
    	 * логика учитывает нажата или нет отметка "в работе"
    	 */
    	
    	if(searchcheckinbound == null){
		    	try {
			    	if(p.matcher(petit.getDateBegin()).matches()) {
						criteria.add(Restrictions.ge("b.date_end", df.parse(petit.getDateBegin().concat(" 0:00:00.0"))	));
			    	}
			    	if(p.matcher(petit.getDateEnd()).matches()) {
			    		criteria.add(Restrictions.le("b.date_end", df.parse(petit.getDateEnd().concat(" 23:59:00.0"))	));
			    	}
			    	
		    	} catch (ParseException e) {
					e.printStackTrace();
				}
    	}  
    	else{ 
    		Date b = null;
    		criteria.add(Restrictions.isNull("b.date_end"));
    		//criteria.add(Restrictions.eq("b.date_end", b));
    		
    		if(p.matcher(petit.getDateBegin()).matches()) {
	    		
				criteria.add(Restrictions.ge("p.dateInput", petit.getDateBegin()	));
    		}
	    	if(p.matcher(petit.getDateEnd()).matches()) {
	    		criteria.add(Restrictions.le("p.dateInput", petit.getDateEnd()	));
	    	}
    	}
    	
    	
    	
    	if(username.contains("smo_ingos")) {
    		criteria.add( Restrictions.in( "username", new String[] { "ИНГОССТРАХ","smo_ingos","smo_ingos_01", "call5003", "callnight5003" } ) );
    	}
    	if(username.equals("smo_simaz")) {
    		criteria.add( Restrictions.in( "username", new String[] { "СИМАЗ","smo_simaz", "call5001", "callnight5001" } ) );
    	}
    	if(username.contains("smo_rosno")) {
    		criteria.add( Restrictions.in( "username", new String[] { "РОСНО","smo_rosno", "smo_rosno_01","smo_rosno_02", "smo_rosno_03","smo_rosno_04", "smo_rosno_05","smo_rosno_06", "smo_rosno_07",
    				"smo_rosno_08", "smo_rosno_09","smo_rosno_10", "smo_rosno_11","smo_rosno_12", "smo_rosno_13","smo_rosno_14", "smo_rosno_15","smo_rosno_16","smo_rosno_17", "smo_rosno_18","smo_rosno_19", "smo_rosno_20",
    				"smo_rosno_21", "smo_rosno_22","smo_rosno_23", "smo_rosno_24","smo_rosno_25", "smo_rosno_26","smo_rosno_27", "smo_rosno_28","smo_rosno_29","smo_rosno_30", "smo_rosno_31","smo_rosno_32", "smo_rosno_33",
    				"smo_rosno_34", "smo_rosno_35","smo_rosno_36", "smo_rosno_37","smo_rosno_38", "smo_rosno_39","smo_rosno_40", "smo_rosno_41","smo_rosno_","smo_rosno_42", "smo_rosno_43","smo_rosno_44", "smo_rosno_45",
    				"call5002", "callnight5002" } ) );
    	}
    	
    	criteria.addOrder(Order.desc("id"));
    	
    	criteria.setMaxResults(10000);

    	return criteria.list();
    }
    
    public static boolean isGetter(Method method) {
    	if (Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0) {
    		if (method.getName().matches("^get[A-Z].*") && !method.getReturnType().equals(void.class)) return true;
    	}
    	return false;
   	}

	public Petit getPetit(Integer petitId) {
	 	Petit petit = (Petit) sessionFactory.getCurrentSession().get(Petit.class, petitId);
    	
	 	if(petit.getDateInput() !=null)
		petit.setDateInput(petit.getDateInput().substring(8, 10) + "." + petit.getDateInput().substring(5, 7) + "." + petit.getDateInput().substring(0, 4));
	 	//petit.getBlockger2016().setDate_close(petit.getBlockger2016().getDate_close().substring(8, 10) + "."+petit.getBlockger2016().getDate_close().substring(5, 7) + "."+petit.getBlockger2016().getDate_close().substring(0, 4));
	 	
		return petit;
	}
	
	
	
	public void close(Integer petitId) {
		Petit petit = (Petit) sessionFactory.getCurrentSession().get(Petit.class, petitId);
		petit.getBlockger2016().setState(4);
		petit.getBlockger2016().setDate_close(new Date());
		petit.getBlockger2016().setPetit(petit);
		
		sessionFactory.getCurrentSession().saveOrUpdate(petit);
    	
	 	
	}



	public List<Date> getMaxDate() {
		Query query = null;
		query = sessionFactory.getCurrentSession().createQuery("select max(t.dateworked) from Callnight_markerday t");
		return query.list();
		
	}



	@Override
	public List<domain.Calendar> getCeleb(Date date) throws ParseException {
		Date dt = date;
		Query query = null;
		query = sessionFactory.getCurrentSession().createQuery("from Calendar t where trunc(t.num_day)=:dt)");
		query.setTimestamp("dt", dt);
		return query.list();
	}
}