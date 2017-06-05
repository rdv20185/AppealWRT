package domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "block_subtype")
public class Subtype implements java.io.Serializable {
	
	
	
	public Subtype() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="id_types")
	private Integer id_types;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PETIT", referencedColumnName="ID")
    @JsonIgnore
	private Petit petit_sub;
	
	
	public Petit getPetit() {
		return petit_sub;
	}
	
	public void setPetit(Petit petit) {
		this.petit_sub = petit;
	}
	
	
	
	
	private String subcause;
	private String subrectif;
	
	
	
	public String getSubcause() {
		return subcause;
	}
	public void setSubcause(String subcause) {
		this.subcause = subcause;
	}
	public String getSubrectif() {
		return subrectif;
	}
	public void setSubrectif(String subrectif) {
		this.subrectif = subrectif;
	}
	public Integer getId_types() {
		return id_types;
	}
	public void setId_types(Integer id_types) {
		this.id_types = id_types;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subtype [id_types=");
		builder.append(id_types);
		builder.append(", subcause=");
		builder.append(subcause);
		builder.append(", subrectif=");
		builder.append(subrectif);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
}
