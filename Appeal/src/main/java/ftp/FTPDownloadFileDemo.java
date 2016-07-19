package ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
 

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import service.nightcall.NightCall;
 
/**
 * A program demonstrates how to upload files from local computer to a remote
 * FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FTPDownloadFileDemo {
	
	private static final Logger logger = Logger.getLogger(FTPDownloadFileDemo.class);
 
	public void startFtp(Calendar conrol,Calendar date_on){
    	FTPClient ftpClient = new FTPClient();
    	
    	try {
	        String 	server = Option.getDirectory("ftp_server");
	        int port = Integer.valueOf(Option.getDirectory("ftp_port"));
	        String user = Option.getDirectory("username");
	        String pass = Option.getDirectory("pass");
        	
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            String directoryFrom = Option.getDirectory("dirfrom"); 
            FTPFile[] files = ftpClient.listFiles(directoryFrom);
            for (FTPFile file : files) {
                String details = file.getName();
                if (file.isDirectory()) {
                    details = "[" + details + "]";
                }
                details += "\t\t" + file.getSize();
                details += "\t\t" + file.getTimestamp().getTime().toString();
                
                // выставляем время у папки, которая на ftp server тк там другой пояс
                file.getTimestamp().set(Calendar.HOUR, file.getTimestamp().get(Calendar.HOUR)+6);
                
                if(file.getTimestamp().after(conrol) && file.getTimestamp().before(date_on))
                {
                	// name folder
                	String remotePath = directoryFrom+file.getName();
                	// get list *.wav files of this folder
                	FTPFile[] filesinDirectory = ftpClient.listFiles(remotePath);
                	for (FTPFile fi : filesinDirectory) {
                		
                        // set time GMT on the *.wav files
                        fi.getTimestamp().set(Calendar.HOUR, fi.getTimestamp().get(Calendar.HOUR)+6);
                        
                		if(fi.getTimestamp().after(conrol) && fi.getTimestamp().before(date_on))
                		{
                			// set a name wav files
                			String remoteFile = fi.getName();
	                			
	                			String remoteFile1 = remotePath+"/"+remoteFile;
	                			String directoryTo = Option.getDirectory("dirTo");
	                			File downloadFile1 = new File(directoryTo+remoteFile);
	                            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
	                            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
	                            outputStream1.close();
	                 
	                            if (success) {
	                            	if(logger.isInfoEnabled()){ logger.info("---> Файл был успешно загружен "+ downloadFile1.getAbsolutePath());}
	                            }else
	                            {
	                            	if(logger.isInfoEnabled()){ logger.info("!!!! Произошла ошибка в загрузке файла "+ downloadFile1.getAbsolutePath());}
	                            }
                		}
                		
                		
                	}		
                }
            }
            
            
            
 
            // APPROACH #1: using retrieveFile(String, OutputStream)
            /*            String remoteFile1 = "/mnt/sda1/rec/2016-04-26-0000/2016-04-26_16-04-36_1604-1601.wav";
            File downloadFile1 = new File("D:/Appeals3/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/Appeal/night_calls_working/2016-04-26_16-04-36_1604-1601.wav");
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            outputStream1.close();
 
            if (success) {
                System.out.println("File #1 has been downloaded successfully. "+ downloadFile1.getAbsolutePath());
            }else
            {
            	System.out.println("File #1 bad");
            }
 																										
            // APPROACH #2: using InputStream retrieveFileStream(String)
            String remoteFile2 = "/mnt/sda1/rec/2016-04-26-0000/2016-04-26_16-04-36_1604-1601.wav";
            File downloadFile2 = new File("D:/download/song.wav");
            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream2.write(bytesArray, 0, bytesRead);
            }
 
           boolean success = ftpClient.completePendingCommand();
            if (success) {
                System.out.println("File #2 has been downloaded successfully.");
            }
            outputStream2.close();
            inputStream.close();
            */																						
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
  
    	
}
