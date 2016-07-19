package domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_CALLNIGHT_MARKERDAY")
public class Callnight_markerday implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Callnight_markerday [id=" + id + ", dateworked=" + dateworked + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
	
    @Column(name = "DATE_WORKED")
    @Temporal(TemporalType.DATE)
    private Date dateworked;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateworked() {
		return dateworked;
	}

	public void setDateworked(Date dateworked) {
		this.dateworked = dateworked;
	}
    
    	
}