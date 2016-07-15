package domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CALEND")
public class Calendar implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "NUM_DAY")
    private Date num_day;
    
    @Column(name = "WEEKEND")
    private String weekand;
    
    @Column(name = "CELEBR")
    private String celebr;

	public Date getNum_day() {
		return num_day;
	}

	public void setNum_day(Date num_day) {
		this.num_day = num_day;
	}


	public String getWeekand() {
		return weekand;
	}

	public void setWeekand(String weekand) {
		this.weekand = weekand;
	}

	public String getCelebr() {
		return celebr;
	}

	public void setCelebr(String celebr) {
		this.celebr = celebr;
	}

	@Override
	public String toString() {
		return "Calendar [num_day=" + num_day + ", weekand=" + weekand + ", celebr=" + celebr + "]";
	}

	    
    	
}