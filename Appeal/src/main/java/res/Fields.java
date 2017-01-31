package res;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
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
	
	
	public  static Map<String, String> getfirsttfoms() throws UnsupportedEncodingException {
		String test = str +"listfortfoms.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getPropertiesStr(test);
	}
	public  static Map<String, String> getfirstingos() throws UnsupportedEncodingException {
		String test = str+"listforingos.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getPropertiesStr(test);
	}
	
	public  static Map<String, String> getfirstsimaz() throws UnsupportedEncodingException {
		String test = str+"listforsimaz.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getPropertiesStr(test);
	}
	
	public  static Map<String, String> getfirstrosno() throws UnsupportedEncodingException {
		String test = str+"listforrosno.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getPropertiesStr(test);
	}
	
	public  static Map<String, String> getProperties() throws UnsupportedEncodingException {
		String test = str+"listforger.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getPropertiesStr(test);
	}
	
	public static Map<Integer, String> getSource() throws UnsupportedEncodingException {
		String test = str+"source.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getPresent() throws UnsupportedEncodingException {
		String test = str+"present.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getInbound_from() throws UnsupportedEncodingException {
		String test = str+"inbound_from.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getPresentforFL() throws UnsupportedEncodingException {
		String test = str+"presentforfl.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getConect() throws UnsupportedEncodingException {
		String test = str+"conect.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getConectforFL() throws UnsupportedEncodingException {
		String test = str+"conectforfl.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getIntermed() throws UnsupportedEncodingException {
		String test = str+"intermed.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getType() throws UnsupportedEncodingException {
		String test = str+"type.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getTer() throws UnsupportedEncodingException {
		String test = str+"ter.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getMo() throws UnsupportedEncodingException {
		String test = str+"mo.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getInsur() throws UnsupportedEncodingException {
		String test = str+"insur.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getPlace() throws UnsupportedEncodingException {
		String test = str+"place.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getValid() throws UnsupportedEncodingException {
		String test = str+"valid.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
	}
	
	public static Map<Integer, String> getHsp() throws UnsupportedEncodingException {
		String test = str+"hsp.txt";
		test = URLDecoder.decode(test, "UTF-8");
		test = new File(test).getPath();
		
		return getProperties(test);
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
