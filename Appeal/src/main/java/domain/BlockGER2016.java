package domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "T_BLOCK_GER")
public class BlockGER2016 {

	
	private Integer idblockger2016;

	private Petit petit;
	
	public BlockGER2016() {
	}
	
	//дата завершения обращения
	//@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date date_end;
	//крайняя дата когда должен быть дан ответ
	private String date_plan_end;
	// Дата Закрытия обращения
	@Temporal(TemporalType.DATE)
	private Date date_close;
	// Номер письма (исходящее)
	private String letter_out_num;
	// Дата письма (исходящее)
	private Date letter_out_date;
	// пришло из минфина, минзндава...ставится отметка входящего письма.
	private String inbound_from;
	// claim_inshur
	private String claim_inshur;
	
	public String getClaim_inshur() {
		return claim_inshur;
	}

	public void setClaim_inshur(String claim_inshur) {
		this.claim_inshur = claim_inshur;
	}

	public String getInbound_from() {
		return inbound_from;
	}

	public void setInbound_from(String inbound_from) {
		this.inbound_from = inbound_from;
	}

	// Тип регистратора
	private Integer regsource_id;
	// Регистратор (по именим) кто создал зарегистрировал
	private String regname;
	// Номер регистрации (запослняется для взаимодествия различных структур - 1-м и 2-м уровнем)
	private String regnum;
	// Дата создания обращения в бд (не путать с date_input)
	private String date_create;

	// Дата изменения редактирования
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd kk:mm:ss.S", timezone="GMT+6")
	private Date date_change;
	// Ссылка на файл  звонка (ночного... )
	private String filecall;
	// видМП. Добавлено Кузнецовой ИН 02.03.17
	private Integer typempid;
	
	private Integer OTV_KON;
	
	public Integer getTypempid() {
		return typempid;
	}

	public Integer getOTV_KON() {
		return OTV_KON;
	}
	
	public void setTypempid(Integer typempid) {
		this.typempid = typempid;
	}

	public void setOTV_KON(Integer OTV_KON) {
		this.OTV_KON = OTV_KON;
	}
	
	public Date getDate_end() {
		return date_end;
	}

	public void setDate_end(Date date_end) {
		this.date_end = date_end;
	}

	
	public String getLetter_out_num() {
		return letter_out_num;
	}

	public void setLetter_out_num(String letter_out_num) {
		this.letter_out_num = letter_out_num;
	}

	public Date getLetter_out_date() {
		return letter_out_date;
	}

	public void setLetter_out_date(Date letter_out_date) {
		this.letter_out_date = letter_out_date;
	}

	public Date getDate_change() {
		return date_change;
	}

	public void setDate_change(Date date_change) {
		this.date_change = date_change;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/*
	   статус записи
	   1-создан
	   2-в работе
	   3-завершен
	   4-закрыт
	 */
	private Integer state;
	

	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "petit"))
	@Id
	@GeneratedValue(generator = "gen")
	@Column(name="ID_GER")
	public Integer getIdblockger2016() {
		return idblockger2016;
	}

	

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@JsonIgnore
	public Petit getPetit() {
		return petit;
	}

	
	public void setPetit(Petit petit) {
		this.petit = petit;
	}


	public void setIdblockger2016(Integer idblockger2016) {
		this.idblockger2016 = idblockger2016;
	}

	public Integer getRegsource_id() {
		return regsource_id;
	}

	public void setRegsource_id(Integer regsource_id) {
		this.regsource_id = regsource_id;
	}

	public String getRegname() {
		return regname;
	}

	public void setRegname(String regname) {
		this.regname = regname;
	}

	public String getRegnum() {
		return regnum;
	}

	public void setRegnum(String regnum) {
		this.regnum = regnum;
	}

	@Transient
	public String getDate_create() {
		return date_create;
	}

	public void setDate_create(String date_create) {
		this.date_create = date_create;
	}

	@Override
	public String toString() {
		return "BlockGER2016 [idblockger2016=" + idblockger2016 + ", date_end=" + date_end + ", date_close="
				+ date_close + ", letter_out_num=" + letter_out_num
				+ ", letter_out_date=" + letter_out_date + ", regsource_id="
				+ regsource_id + ", regname=" + regname + ", regnum=" + regnum
				+ ", date_create=" + date_create + ", date_change="
				+ date_change + ",inbound_from="+ inbound_from +",date_plan_end="+ date_plan_end +",claim_inshur="+ claim_inshur +"typempid="+typempid+" state=" + state + ",filecall ="+ filecall +"]";
	}

	public Date getDate_close() {
		return date_close;
	}

	public void setDate_close(Date date_close) {
		this.date_close = date_close;
	}

	public String getFilecall() {
		return filecall;
	}

	public void setFilecall(String filecall) {
		this.filecall = filecall;
	}

	public String getDate_plan_end() {
		return date_plan_end;
	}

	public void setDate_plan_end(String date_plan_end) {
		this.date_plan_end = date_plan_end;
	}

	
}
