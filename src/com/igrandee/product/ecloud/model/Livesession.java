package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;



@Repository
@Scope("prototype")
@Entity
@Table(name="livesession")
public class Livesession implements Serializable{

	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true,nullable=false)
	private int livesessionid;

	@Column(length=500)
	private String description;
	
	@Column(length=50)
	private String starttime;
	
	@Column(length=50)
	private String endtime;
	
	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course courseid;
	
	@Column(length=45)
	private String fromipaddress;
	
	@Column(length=1)
	private String livesessionstatus;

	@Column(length=45)
	private String price;
	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}

	private Timestamp createddate;

	private Timestamp scheduledate;
	
	private Timestamp startdatetime;
	
	private Timestamp enddatetime;

	public Livesession(){}


	public int getLivesessionid() {
		return livesessionid;
	}


	public void setLivesessionid(int livesessionid) {
		this.livesessionid = livesessionid;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getStarttime() {
		return starttime;
	}


	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}


	public String getEndtime() {
		return endtime;
	}


	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}


	public Course getCourseid() {
		return courseid;
	}


	public void setCourseid(Course courseid) {
		this.courseid = courseid;
	}


	public String getFromipaddress() {
		return fromipaddress;
	}


	public void setFromipaddress(String fromipaddress) {
		this.fromipaddress = fromipaddress;
	}
	public String getLivesessionstatus() {
		return livesessionstatus;
	}


	public void setLivesessionstatus(String livesessionstatus) {
		this.livesessionstatus = livesessionstatus;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}


	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}


	public Timestamp getScheduledate() {
		return scheduledate;
	}


	public void setScheduledate(Timestamp scheduledate) {
		this.scheduledate = scheduledate;
	}


	public Timestamp getStartdatetime() {
		return startdatetime;
	}


	public void setStartdatetime(Timestamp startdatetime) {
		this.startdatetime = startdatetime;
	}


	public Timestamp getEnddatetime() {
		return enddatetime;
	}


	public void setEnddatetime(Timestamp enddatetime) {
		this.enddatetime = enddatetime;
	}

	
}	

