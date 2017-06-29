package domain;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	private Integer subhspId;
	private Integer subtypempid;
	private Integer submoId;
	private Integer subinsurId;
	private Integer subvalidId;
	@Column(columnDefinition = "FLOAT(5,2)")
	private Double subcompens;
	private String subsatisf;
	private String subcompensSource;
	//@Column(precision=10, scale=2)
	//private BigDecimal subcompensSum;
	@Column(columnDefinition = "FLOAT(5,2)")
	private Double subcompensSum;
	private String subcompensCode;
	@Size(max =501, message="Длина примечания не более 500 символов")
	private String subcauseNote;
	
	
	
	
	
	
	
	
	
	
	
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

	public Integer getSubhspId() {
		return subhspId;
	}

	public void setSubhspId(Integer subhspId) {
		this.subhspId = subhspId;
	}

	public Integer getSubtypempid() {
		return subtypempid;
	}

	public void setSubtypempid(Integer subtypempid) {
		this.subtypempid = subtypempid;
	}

	public Integer getSubmoId() {
		return submoId;
	}

	public void setSubmoId(Integer submoId) {
		this.submoId = submoId;
	}

	public Integer getSubinsurId() {
		return subinsurId;
	}

	public void setSubinsurId(Integer subinsurId) {
		this.subinsurId = subinsurId;
	}

	public Integer getSubvalidId() {
		return subvalidId;
	}

	public void setSubvalidId(Integer subvalidId) {
		this.subvalidId = subvalidId;
	}

	public Double getSubcompens() {
		return subcompens;
	}

	public void setSubcompens(Double subcompens) {
		this.subcompens = subcompens;
	}

	public String getSubsatisf() {
		return subsatisf;
	}

	public void setSubsatisf(String subsatisf) {
		this.subsatisf = subsatisf;
	}

	public String getSubcompensSource() {
		return subcompensSource;
	}

	public void setSubcompensSource(String subcompensSource) {
		this.subcompensSource = subcompensSource;
	}

	
	public String getSubcompensCode() {
		return subcompensCode;
	}

	public void setSubcompensCode(String subcompensCode) {
		this.subcompensCode = subcompensCode;
	}

	public String getSubcauseNote() {
		return subcauseNote;
	}

	public void setSubcauseNote(String subcauseNote) {
		this.subcauseNote = subcauseNote;
	}

	public Double getSubcompensSum() {
		return subcompensSum;
	}

	public void setSubcompensSum(Double subcompensSum) {
		this.subcompensSum = subcompensSum;
	}

	
		
	
	
	
	
}
