package ftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Option {

	public static String getDirectory(String path) throws IOException
	 {
		 Properties properties = new Properties();
		 
		 InputStream is = FTPDownloadFileDemo.class.getClassLoader().getResourceAsStream("directoriesftp.properties");
		 properties.load(is);
			
		return properties.getProperty(path);
	 }

}
