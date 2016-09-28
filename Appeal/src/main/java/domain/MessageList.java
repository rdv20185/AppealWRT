package domain;

import java.io.Serializable;

public class MessageList implements Serializable{
	
	private Object Id;
	private String Role;
	private String User;
	private String Process;
	private String Time;

	public MessageList(Object id, String role, String user, String process, String time) {
		super();
		Id = id;
		Role = role;
		User = user;
		Process = process;
		Time = time;
	}
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	public Object getId() {
		return Id;
	}
	public void setId(Object id) {
		Id = id;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getProcess() {
		return Process;
	}
	public void setProcess(String process) {
		Process = process;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}


	
}
