package service.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javassist.bytecode.stackmap.BasicBlock.Catch;

@Component
public class Converter {
	
	@Autowired
    private ServletContext servletcontext;

	
   
	private Map<String,List<String>> map; 
	   
   public Converter(){
		
   }

   @PostConstruct
	private Map<String,List<String>> process() throws ParserConfigurationException, SAXException, IOException{
		
			 
		 File fXmlFile = new File(servletcontext.getRealPath("/WEB-INF/spring/security.xml"));
		 Map<String,List<String>> map  = new HashMap<String,List<String>>();
		   
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		 
		 Document doc = dBuilder.parse(fXmlFile);
		 doc.getDocumentElement().normalize();
		 
		 
		 NodeList nl = doc.getElementsByTagName("user");
		 for (int i = 0; i < nl.getLength(); i++)
		 {
			 Element movieElement = (Element) nl.item(i);
			 
			 String[] role = movieElement.getAttributes().getNamedItem("authorities").getNodeValue().split(",");
			 for (int j = 0; j < role.length; j++) {
				if(map.get(role[j]) != null){
					List<String> ls = map.get(role[j]);
					ls.add(movieElement.getAttributes().getNamedItem("name").getNodeValue());
					map.put(role[j], ls);	
				}else{
					List<String> ls = new ArrayList<String>();
					ls.add(movieElement.getAttributes().getNamedItem("name").getNodeValue());
					map.put(role[j], ls);
				}
				
			 }
		 }
		 
		 System.out.println(map.get("ROLE_TFOMS"));
		 System.out.println(map.get("ROLE_ADMIN"));
		 System.out.println(map.get("ROLE_SMO"));
		 System.out.println(map.get("ROLE_ER"));
		 
		 return this.map = map;
	   }


	public Map<String, List<String>> getMap() {
		return map;
	}
		 


}
