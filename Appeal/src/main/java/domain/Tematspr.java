package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_TEMAT_SPR")
public class Tematspr {
	
	private int tematsprId;	
	private String tematsprName;	
	
	public Tematspr() {
		this.tematsprId = 0;
	}	
	
	public Tematspr(String tematspr, int tematsprId) {
		this.tematsprName = tematspr;
		this.tematsprId = tematsprId;
	}	
	
	@Id
	@Column(name="TEMAT_SPR_ID")
	public int gettematsprId() {
		return tematsprId;
	}
	
	@Column(name="TEMA_SPR")
	public String gettematsprName(){
		return tematsprName;
	}
	
	public void settematsprId(int i){
		this.tematsprId = i;		
	}
	
	public void settematsprName(String s){
		this.tematsprName = s;
	}	
	
}