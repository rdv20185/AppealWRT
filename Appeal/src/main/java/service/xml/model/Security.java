package service.xml.model;

public class Security {

	private String name;
	private String password;
	private String authorities;
	
	public void setName(String name){
		this.name= name;
	}
	
	public void setPassword(String password){
			this.password = password;
	}
	
	public void setAuthorities(String authorities){
		this.authorities = authorities;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	public String getAuthorities(){
		return this.authorities;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Security [name=");
		builder.append(name);
		builder.append(", password=");
		builder.append(password);
		builder.append(", authorities=");
		builder.append(authorities);
		builder.append("]");
		return builder.toString();
	}
	
}
