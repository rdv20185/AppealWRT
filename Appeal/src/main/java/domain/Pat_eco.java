package domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Pat_eco {
	
	@Column(name="ID")
	private String id;
	@Column(name="FAM")
	private String fam;
	@Column(name="IM")
	private String im;
	@Column(name="OT")
	private String ot;
	@Column(name="DR")
	private String dr;
	@Column(name="DAT_END")
	private String dat_end;
	@Column(name="MES")
	private String mes;
	@Column(name="KSG")
	private String ksg;
	@Column(name="SUMMA")
	private String summa;
	@Column(name="O_ID")
	private String o_id;
	@Column(name="O_DATE")
	private String o_date;
	@Column(name="O_MKB")
	private String o_mkb;
	@Column(name="N_ID")
	private String n_id;
	@Column(name="N_DATE")
	private String n_date;
	@Column(name="N_MKB")
	private String n_mkb;
	@Column(name="R_ID")
	private String r_id;
	@Column(name="R_DATE")
	private String r_date;
	@Column(name="DATE_INSERT")
	private String date_insert;
	@Column(name="DATE_UPDATE")
	private String date_update;
	@Column(name="PATIONT_MTR")
	private String pationt_MTR;
	@Column(name="STATUS")
	private String status;
	
	public Pat_eco() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFam() {
		return fam;
	}
	public void setFam(String fam) {
		this.fam = fam;
	}
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}
	public String getOt() {
		return ot;
	}
	public void setOt(String ot) {
		this.ot = ot;
	}
	public String getDr() {
		return dr;
	}
	public void setDr(String dr) {
		this.dr = dr;
	}
	public String getDat_end() {
		return dat_end;
	}
	public void setDat_end(String dat_end) {
		this.dat_end = dat_end;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getKsg() {
		return ksg;
	}
	public void setKsg(String ksg) {
		this.ksg = ksg;
	}
	public String getSumma() {
		return summa;
	}
	public void setSumma(String summa) {
		this.summa = summa;
	}
	public String getO_id() {
		return o_id;
	}
	public void setO_id(String o_id) {
		this.o_id = o_id;
	}
	public String getO_date() {
		return o_date;
	}
	public void setO_date(String o_date) {
		this.o_date = o_date;
	}
	public String getO_mkb() {
		return o_mkb;
	}
	public void setO_mkb(String o_mkb) {
		this.o_mkb = o_mkb;
	}
	public String getN_id() {
		return n_id;
	}
	public void setN_id(String n_id) {
		this.n_id = n_id;
	}
	public String getN_date() {
		return n_date;
	}
	public void setN_date(String n_date) {
		this.n_date = n_date;
	}
	public String getN_mkb() {
		return n_mkb;
	}
	public void setN_mkb(String n_mkb) {
		this.n_mkb = n_mkb;
	}
	public String getR_id() {
		return r_id;
	}
	public void setR_id(String r_id) {
		this.r_id = r_id;
	}
	public String getR_date() {
		return r_date;
	}
	public void setR_date(String r_date) {
		this.r_date = r_date;
	}
	public String getDate_insert() {
		return date_insert;
	}
	public void setDate_insert(String date_insert) {
		this.date_insert = date_insert;
	}
	public String getDate_update() {
		return date_update;
	}
	public void setDate_update(String date_update) {
		this.date_update = date_update;
	}
	public String getPationt_MTR() {
		return pationt_MTR;
	}
	public void setPationt_MTR(String pationt_MTR) {
		this.pationt_MTR = pationt_MTR;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pat_eco [id=");
		builder.append(id);
		builder.append(", fam=");
		builder.append(fam);
		builder.append(", im=");
		builder.append(im);
		builder.append(", ot=");
		builder.append(ot);
		builder.append(", dr=");
		builder.append(dr);
		builder.append(", dat_end=");
		builder.append(dat_end);
		builder.append(", mes=");
		builder.append(mes);
		builder.append(", ksg=");
		builder.append(ksg);
		builder.append(", summa=");
		builder.append(summa);
		builder.append(", o_id=");
		builder.append(o_id);
		builder.append(", o_date=");
		builder.append(o_date);
		builder.append(", o_mkb=");
		builder.append(o_mkb);
		builder.append(", n_id=");
		builder.append(n_id);
		builder.append(", n_date=");
		builder.append(n_date);
		builder.append(", n_mkb=");
		builder.append(n_mkb);
		builder.append(", r_id=");
		builder.append(r_id);
		builder.append(", r_date=");
		builder.append(r_date);
		builder.append(", date_insert=");
		builder.append(date_insert);
		builder.append(", date_update=");
		builder.append(date_update);
		builder.append(", pationt_MTR=");
		builder.append(pationt_MTR);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
									


}
