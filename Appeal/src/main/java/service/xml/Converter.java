package service.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import service.xml.model.Security;
import javax.servlet.ServletContext;

@Component
public class Converter {
	
	@Autowired
	@Qualifier("castorMarshaller")
	CastorMarshaller castorMarshaller;
	@Autowired
	ServletContext servletContext;
	
	private Security[] security;
	private Unmarshaller unmarshaller;
	
	

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	public void load() throws IOException {
		File f = new File( servletContext.getRealPath("/resources/report/info.xml"));
		
		setUnmarshaller(this.castorMarshaller);
		FileInputStream is = null;
		try {
			System.out.println("--- Unmarshaller ---");
			is = new FileInputStream(f);
			this.security = (Security[]) this.unmarshaller.unmarshal(new StreamSource(is));
			System.out.println("Info loaded from xml : " + Arrays.toString(security));
		} finally {
			if (is != null) {
				is.close();
			}
		}

	}
	

}
