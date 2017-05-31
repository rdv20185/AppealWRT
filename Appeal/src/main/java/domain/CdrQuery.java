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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CdrQuery")
public class CdrQuery implements Serializable{
	
	public CdrQuery(String data_start_talk1, String duration_conversation, String cause, String info, String ip_caller,
			String type_output, String name_caller, String num1_caller, String num2_caller, String categ1_caller,
			String categ2_caller, String redirect_num1, String redirect_num2, String ip_called, String type_input,
			String name_called, String num1_called, String num2_called, String data_start_talk2, String data_end_talk,
			String mark_redirect1, String mark_redirect2, String firs_bay) {
		
		this.data_start_talk1 = data_start_talk1;
		this.duration_conversation = duration_conversation;
		this.cause = cause;
		this.info = info;
		this.ip_caller = ip_caller;
		this.type_output = type_output;
		this.name_caller = name_caller;
		this.num1_caller = num1_caller;
		this.num2_caller = num2_caller;
		this.categ1_caller = categ1_caller;
		this.categ2_caller = categ2_caller;
		this.redirect_num1 = redirect_num1;
		this.redirect_num2 = redirect_num2;
		this.ip_called = ip_called;
		this.type_input = type_input;
		this.name_called = name_called;
		this.num1_called = num1_called;
		this.num2_called = num2_called;
		this.data_start_talk2 = data_start_talk2;
		this.data_end_talk = data_end_talk;
		this.mark_redirect1 = mark_redirect1;
		this.mark_redirect2 = mark_redirect2;
		this.firs_bay = firs_bay;
	}
	
	public CdrQuery() {
		super();
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="cdrid",sequenceName="oracle_seq_cdr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cdrid")  
	private Integer id;
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date date_input = new Date();
    private String  data_start_talk1;
    private String  duration_conversation;
    private String  cause;
    private String  info;
    private String  ip_caller;
    private String  type_output;
    private String  name_caller;
    private String  num1_caller;
    private String  num2_caller;
    private String  categ1_caller;
    private String  categ2_caller;
    private String  redirect_num1;
    private String  redirect_num2;
    private String  ip_called;
    private String  type_input;
    private String  name_called;
    private String  num1_called;
    private String  num2_called;
    private String  data_start_talk2;
    private String  data_end_talk;
    private String  mark_redirect1;
    private String  mark_redirect2;
    private String  firs_bay;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getData_start_talk1() {
		return data_start_talk1;
	}
	public void setData_start_talk1(String data_start_talk1) {
		this.data_start_talk1 = data_start_talk1;
	}
	public String getDuration_conversation() {
		return duration_conversation;
	}
	public void setDuration_conversation(String duration_conversation) {
		this.duration_conversation = duration_conversation;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getIp_caller() {
		return ip_caller;
	}
	public void setIp_caller(String ip_caller) {
		this.ip_caller = ip_caller;
	}
	public String getType_output() {
		return type_output;
	}
	public void setType_output(String type_output) {
		this.type_output = type_output;
	}
	public String getName_caller() {
		return name_caller;
	}
	public void setName_caller(String name_caller) {
		this.name_caller = name_caller;
	}
	public String getNum1_caller() {
		return num1_caller;
	}
	public void setNum1_caller(String num1_caller) {
		this.num1_caller = num1_caller;
	}
	public String getNum2_caller() {
		return num2_caller;
	}
	public void setNum2_caller(String num2_caller) {
		this.num2_caller = num2_caller;
	}
	public String getCateg1_caller() {
		return categ1_caller;
	}
	public void setCateg1_caller(String categ1_caller) {
		this.categ1_caller = categ1_caller;
	}
	public String getCateg2_caller() {
		return categ2_caller;
	}
	public void setCateg2_caller(String categ2_caller) {
		this.categ2_caller = categ2_caller;
	}
	public String getRedirect_num1() {
		return redirect_num1;
	}
	public void setRedirect_num1(String redirect_num1) {
		this.redirect_num1 = redirect_num1;
	}
	public String getRedirect_num2() {
		return redirect_num2;
	}
	public void setRedirect_num2(String redirect_num2) {
		this.redirect_num2 = redirect_num2;
	}
	public String getIp_called() {
		return ip_called;
	}
	public void setIp_called(String ip_called) {
		this.ip_called = ip_called;
	}
	public String getType_input() {
		return type_input;
	}
	public void setType_input(String type_input) {
		this.type_input = type_input;
	}
	public String getName_called() {
		return name_called;
	}
	public void setName_called(String name_called) {
		this.name_called = name_called;
	}
	public String getNum1_called() {
		return num1_called;
	}
	public void setNum1_called(String num1_called) {
		this.num1_called = num1_called;
	}
	public String getNum2_called() {
		return num2_called;
	}
	public void setNum2_called(String num2_called) {
		this.num2_called = num2_called;
	}
	public String getData_start_talk2() {
		return data_start_talk2;
	}
	public void setData_start_talk2(String data_start_talk2) {
		this.data_start_talk2 = data_start_talk2;
	}
	public String getData_end_talk() {
		return data_end_talk;
	}
	public void setData_end_talk(String data_end_talk) {
		this.data_end_talk = data_end_talk;
	}
	public String getMark_redirect1() {
		return mark_redirect1;
	}
	public void setMark_redirect1(String mark_redirect1) {
		this.mark_redirect1 = mark_redirect1;
	}
	public String getMark_redirect2() {
		return mark_redirect2;
	}
	public void setMark_redirect2(String mark_redirect2) {
		this.mark_redirect2 = mark_redirect2;
	}
	public String getFirs_bay() {
		return firs_bay;
	}
	public void setFirs_bay(String firs_bay) {
		this.firs_bay = firs_bay;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CdrQuery [id=");
		builder.append(id);
		builder.append(", data_start_talk1=");
		builder.append(data_start_talk1);
		builder.append(", duration_conversation=");
		builder.append(duration_conversation);
		builder.append(", cause=");
		builder.append(cause);
		builder.append(", info=");
		builder.append(info);
		builder.append(", ip_caller=");
		builder.append(ip_caller);
		builder.append(", type_output=");
		builder.append(type_output);
		builder.append(", name_caller=");
		builder.append(name_caller);
		builder.append(", num1_caller=");
		builder.append(num1_caller);
		builder.append(", num2_caller=");
		builder.append(num2_caller);
		builder.append(", categ1_caller=");
		builder.append(categ1_caller);
		builder.append(", categ2_caller=");
		builder.append(categ2_caller);
		builder.append(", redirect_num1=");
		builder.append(redirect_num1);
		builder.append(", redirect_num2=");
		builder.append(redirect_num2);
		builder.append(", ip_called=");
		builder.append(ip_called);
		builder.append(", type_input=");
		builder.append(type_input);
		builder.append(", name_called=");
		builder.append(name_called);
		builder.append(", num1_called=");
		builder.append(num1_called);
		builder.append(", num2_called=");
		builder.append(num2_called);
		builder.append(", data_start_talk2=");
		builder.append(data_start_talk2);
		builder.append(", data_end_talk=");
		builder.append(data_end_talk);
		builder.append(", mark_redirect1=");
		builder.append(mark_redirect1);
		builder.append(", mark_redirect2=");
		builder.append(mark_redirect2);
		builder.append(", firs_bay=");
		builder.append(firs_bay);
		builder.append("]");
		return builder.toString();
	}
    
    
    
	    
    	
}