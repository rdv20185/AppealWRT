package res;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;


public class Fields {
	
	private static URL url = Fields.class.getResource("Fields.class");
	private static String str = url.getPath().substring(url.getPath().indexOf("C:/"), url.getPath().indexOf("WEB-INF"))+"resources/settings_fields/";
	
	public  static Map<String, String> getfirsttfoms() {
		return getPropertiesStr(str+"listfortfoms.txt");
	}
	public  static Map<String, String> getfirstingos() {
		return getPropertiesStr(str+"listforingos.txt");
	}
	
	public  static Map<String, String> getfirstsimaz() {
		return getPropertiesStr(str+"listforsimaz.txt");
	}
	
	public  static Map<String, String> getfirstrosno() {
		return getPropertiesStr(str+"listforrosno.txt");
	}
	
	public  static Map<String, String> getProperties() {
		return getPropertiesStr(str+"listforger.txt");
	}
	
	public static Map<Integer, String> getSource() {
		return getProperties(str+"source.txt");
	}
	
	public static Map<Integer, String> getPresent() {
		return getProperties(str+"present.txt");
	}
	
	public static Map<Integer, String> getInbound_from() {
		return getProperties(str+"inbound_from.txt");
	}
	
	public static Map<Integer, String> getPresentforFL() {
		return getProperties(str+"presentforfl.txt");
	}
	
	public static Map<Integer, String> getConect() {
		return getProperties(str+"conect.txt");
	}
	
	public static Map<Integer, String> getConectforFL() {
		return getProperties(str+"conectforfl.txt");
	}
	
	public static Map<Integer, String> getIntermed() {
		return getProperties(str+"intermed.txt");
	}
	
	public static Map<Integer, String> getType() {
		return getProperties(str+"type.txt");
	}
	
	public static Map<Integer, String> getTer() {
		return getProperties(str+"ter.txt");
	}
	
	public static Map<Integer, String> getMo() {
		return getProperties(str+"mo.txt");
	}
	
	public static Map<Integer, String> getInsur() {
		return getProperties(str+"insur.txt");
	}
	
	public static Map<Integer, String> getPlace() {
		return getProperties(str+"place.txt");
	}
	
	public static Map<Integer, String> getValid() {
		return getProperties(str+"valid.txt");
	}
	
	public static Map<Integer, String> getHsp() {
		return getProperties(str+"hsp.txt");
	}

	private static Map<Integer, String> getProperties(String filename) {
		Properties prop = new Properties();
		Map<Integer, String> map = new TreeMap<Integer, String>(
					new Comparator<Integer>() {
		 
					@Override
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
		 
				});
		try {
		    InputStream stream = new FileInputStream(new File(filename));
		    InputStreamReader  reader = new InputStreamReader(stream,"UTF-8");
			prop.load(reader);

			Enumeration e = prop.keys();
			while(e.hasMoreElements()) {
				String key = (String)e.nextElement();
				map.put(Integer.parseInt(key), prop.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return map;
	}

	private static Map<String, String> getPropertiesStr(String filename) {
		Properties prop = new Properties();
		Map<String, String> map = new TreeMap<String, String>();
		
		try {
		    InputStream stream = new FileInputStream(new File(filename));
		    InputStreamReader  reader = new InputStreamReader(stream,"UTF-8");
			prop.load(reader);

			Enumeration e = prop.keys();
			while(e.hasMoreElements()) {
				String key = (String)e.nextElement();
				map.put(prop.getProperty(key), prop.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return map;
	}

	
}
