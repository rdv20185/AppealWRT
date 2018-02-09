package ftp.datasource;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;

import ftp.Option;

@Component("ftp_datasource")
public class Ftp_datasource {
 
	private FTPClient ftpClient;
	
	Ftp_datasource(){
		
	}
	
	public FTPClient getConnection() throws IOException{
		
		if(this.ftpClient != null && this.ftpClient.isConnected()){
			return ftpClient;
		}else{
			return connect();			
		}
	}
	
	private  FTPClient connect() throws IOException{
		
		ftpClient = new FTPClient();
		
		String 	server = Option.getDirectory("ftp_server","directoriesftp.properties");
        int port = Integer.valueOf(Option.getDirectory("ftp_port","directoriesftp.properties"));
        String user = Option.getDirectory("username","directoriesftp.properties");
        String pass = Option.getDirectory("pass","directoriesftp.properties");
    	
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        
        return ftpClient;
	}
	
	public void disconnect_ftp(FTPClient ftpClient) throws IOException{
		
		if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
            System.out.println("disconnect");
        }
	}
}
