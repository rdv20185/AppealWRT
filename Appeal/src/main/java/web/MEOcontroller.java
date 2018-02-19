package web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;
import service.MeoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.ReportParams;


/**
 * @author pylypiv.sergey
 * 20/02/17
 *
 */
@Controller
public class MEOcontroller {
	
	@Autowired
    private MeoService meoService;
	@Autowired
    private ServletContext servletcontext;
	
	
    public MEOcontroller() {
    	
    }
    
    @RequestMapping("/reportingMEO.html")
    public String reporting_meo(ModelMap map,HttpServletRequest request) {
    	map.put("dateReport", new ReportParams());
        return "reporting_meo";
    }
    
    @RequestMapping(value = "/report_meo_abortion.html", method = RequestMethod.POST)
    public String report_meo_abortion(@ModelAttribute("dateReport") @Valid ReportParams dateReport, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting_meo";
		
			 // meoService.report_abortion(dateReport, "", "Abortion 2018 year.sql");
				meoService.report_abortion(dateReport, "", dateReport.getTypeQuery());
			
			
    	return "reporting_meo";
	}
    
    @RequestMapping(value = "/cooked_report_meo_abortion", method = RequestMethod.GET)
    public void report_abortion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		File f = new File( servletcontext.getRealPath("/resources/report/meo/report_meo_abortion.xls"));
        downloadFile(request, response, f.getPath());
	}
    
    @RequestMapping(value = "/report_meo_ambulance.html", method = RequestMethod.POST)
    public String report_meo_ambulance(@ModelAttribute("dateReport") @Valid ReportParams dateReport, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException {
		if(bindingResult.hasErrors()) return "reporting_meo";
		
			 meoService.report_ambulance(dateReport, "",dateReport.getTypeQuery());
			
			
    	return "reporting_meo";
	}
    
    @RequestMapping(value = "/cooked_report_meo_ambulance", method = RequestMethod.GET)
    public void report_ambulance(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		File f = new File( servletcontext.getRealPath("/resources/report/meo/ambulance.xls"));
        downloadFile(request, response, f.getPath());
	}
    
    @RequestMapping(value = "/report_meo_eco.html", method = RequestMethod.POST)
    public String report_meo_eco(@ModelAttribute("dateReport") @Valid ReportParams dateReport, BindingResult bindingResult) throws ClassNotFoundException, SQLException, JRException, FileNotFoundException {
		if(bindingResult.hasErrors()) return "reporting_meo";
		
			 meoService.report_eco(dateReport, "",dateReport.getTypeQuery());
			
			
    	return "reporting_meo";
	}
    
    @RequestMapping(value = "/cooked_report_meo_eco", method = RequestMethod.GET)
    public void report_eco(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		File f = new File( servletcontext.getRealPath("/resources/report/meo/report_meo_eco_v1.xls"));
        downloadFile(request, response, f.getPath());
	}
    
    

	private void downloadFile(HttpServletRequest request,
			HttpServletResponse response, String filePath) throws FileNotFoundException,
			IOException {
		ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        String fullPath = "";
		fullPath = filePath;
        
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
 
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
	}
    
   
}
