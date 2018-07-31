package dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
import domain.Subtype;
import service.xml.Converter;

@Repository
public class PetitDAOImpl implements PetitDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
	Converter coverter;

    public void addPetit(Petit petit) {
    	
        sessionFactory.getCurrentSession().saveOrUpdate(petit);
        
    }
   
    @Override
    public void deleteByIdSubtype(Integer id) {
    	
    	Query query = null;
    	query = sessionFactory.getCurrentSession().createQuery("delete Subtype t2 where t2.petit_sub.id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
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
    public List<Petit> listPetit(String username, Set<String> role ) {
    	Query query = null;
    	
    	
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
    	
    	if(username.equals("callnight5001") || username.equals("callnight5002") || username.equals("callnight5003")){
    		query = sessionFactory.getCurrentSession().createQuery(
        			"select t from Petit t, BlockGER2016 t2  where (t.username = :username or t.username = '"+"callnight5003"+"' or t.username = '"+"ТФОМС"+"' or t.username = '"+"callnight5001"+"' or t.username = '"+"callnight5002"+"' or t.username='"+"auto"+"'   or t.username='"+"СИМАЗ"+"' or t.username='"+"РОСНО"+"' or t.username='"+"ИНГОССТРАХ"+"') and"
        					+ " t.id=t2.idblockger2016 and (t2.regname='"+"auto"+"' or t2.regname='"+"callnight5001"+"' or t2.regname='"+"callnight5002"+"' or t2.regname='"+"callnight5003"+"')  order by t.id desc");
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
		
		if(username.equals("mityanina") || username.equals("eremina") || username.equals("user_1")  || username.equals("utyatnikova"))
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
		
		
		if(role.contains("ROLE_INGOS"))
    	{
			String str = ""; 
			for(int i=0;i < coverter.getMap().get("ROLE_INGOS").size(); i++){
				str += "username='"+coverter.getMap().get("ROLE_INGOS").get(i)+"'";
				if(coverter.getMap().get("ROLE_INGOS").size() - 1 != i) str +=" or ";
			}
			System.out.println("SSS "+str);
			query = sessionFactory.getCurrentSession().createQuery(
			"from Petit where "+str+" order by id desc");
            query.setMaxResults(100);
    	}
		
		if(role.contains("ROLE_ROSNO"))
    	{
			String str = ""; 
			for(int i=0;i < coverter.getMap().get("ROLE_ROSNO").size(); i++){
				str += "username='"+coverter.getMap().get("ROLE_ROSNO").get(i)+"'";
				if(coverter.getMap().get("ROLE_ROSNO").size() - 1 != i) str +=" or ";
			}
			System.out.println("SSS "+str);
			query = sessionFactory.getCurrentSession().createQuery(
			"from Petit where "+str+" order by  id desc");
			query.setMaxResults(100);
    	}
		

		if(role.contains("ROLE_SIMAZ"))
    	{
			String str = ""; 
			for(int i=0;i < coverter.getMap().get("ROLE_SIMAZ").size(); i++){
				str += "username='"+coverter.getMap().get("ROLE_SIMAZ").get(i)+"'";
				if(coverter.getMap().get("ROLE_SIMAZ").size() - 1 != i) str +=" or ";
			}
			System.out.println("SSS "+str);
			query = sessionFactory.getCurrentSession().createQuery(
			"from Petit where "+str+" order by  id desc");
            query.setMaxResults(100);
    	}
		
		
		if(role.contains("ROLE_ADMIN"))
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
	public List<Petit> listSearch(Petit petit, String username, String searchcheckinbound, String overdueappeal,Set<String> role) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	
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
        			}else{
        				
        				if(!fieldName.equalsIgnoreCase("username")){
        					
        					criteria.add(Restrictions.eq("p."+fieldName, method.invoke(petit)));
        					
        				}else{
	        				if(method.invoke(petit).equals("ROLE_ROSNO")){
	        					
	        	    			criteria.add( Restrictions.in( "username", getUsernameOfRole("ROLE_ROSNO","ROLE_ER5002") ) );
	        	    			
	        	    		}else if(method.invoke(petit).equals("ROLE_SIMAZ")){
	        	    			
	        	        		criteria.add( Restrictions.in( "username", getUsernameOfRole("ROLE_SIMAZ","ROLE_ER5001") ) );
	        	        		
	        	    		}else if(method.invoke(petit).equals("ROLE_INGOS")){
	        	    			System.out.println("FFFFFF "+Arrays.toString(getUsernameOfRole("ROLE_INGOS","ROLE_ER5003")));
	        	    			
	        	    			criteria.add( Restrictions.in( "username", getUsernameOfRole("ROLE_INGOS","ROLE_ER5003") ) );
	        	    		}
        				}	
        			}
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
    	
    	
    	if(role.contains("ROLE_INGOS")) {
    		
    		criteria.add( Restrictions.in( "username", getUsernameOfRole("ROLE_INGOS","ROLE_ER5003") ) );
    		
    	}else if (role.contains("ROLE_SIMAZ")) {
    		
    		criteria.add( Restrictions.in( "username", getUsernameOfRole("ROLE_SIMAZ","ROLE_ER5001") ) );
    		
    	}else if(role.contains("ROLE_ROSNO")) {
    		
    		criteria.add( Restrictions.in( "username", getUsernameOfRole("ROLE_ROSNO","ROLE_ER5002") ) );
    		
    	}
    	
    	criteria.addOrder(Order.desc("id"));
    	criteria.setMaxResults(10_000);

    	return criteria.list();
    }
    
    private String[] getUsernameOfRole(String role1,String role2){
    	
		String[] stringArray =  coverter.getMap().get(role1).toArray(new String[0]);
		String[] stringArray2 =  coverter.getMap().get(role2).toArray(new String[0]);
		String[] both = Stream.concat(Arrays.stream(stringArray), Arrays.stream(stringArray2)).toArray(String[]::new);
		
		System.out.println("Search on usernames (for log) "+ Arrays.toString(both));
		return both;
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