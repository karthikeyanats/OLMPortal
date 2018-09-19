package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the onlineexam database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="onlineexam")
public class Onlineexam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int onlineexamid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date assigneddate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofexpiry;

	@Column(length=45)
	private String fee;

	@Column(nullable=false, length=45)
	private String feetype;

	@Column(length=1)
	private char onlineexamstatus;

	@Column(length=10)
	private String passmark;

	//bi-directional many-to-one association to Questionsheetmaster
	@ManyToOne
	@JoinColumn(name="qsid")
	private Questionsheetmaster questionsheetmaster;

	public Onlineexam() {
	}

	public int getOnlineexamid() {
		return this.onlineexamid;
	}

	public void setOnlineexamid(int onlineexamid) {
		this.onlineexamid = onlineexamid;
	}

	public Date getAssigneddate() {
		return this.assigneddate;
	}

	public void setAssigneddate(Date assigneddate) {
		this.assigneddate = assigneddate;
	}

	public Date getDateofexpiry() {
		return this.dateofexpiry;
	}

	public void setDateofexpiry(Date dateofexpiry) {
		this.dateofexpiry = dateofexpiry;
	}

	public String getFee() {
		return this.fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFeetype() {
		return this.feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}
 
	public String getPassmark() {
		return this.passmark;
	}

	public void setPassmark(String passmark) {
		this.passmark = passmark;
	}

	public Questionsheetmaster getQuestionsheetmaster() {
		return this.questionsheetmaster;
	}

	public void setQuestionsheetmaster(Questionsheetmaster questionsheetmaster) {
		this.questionsheetmaster = questionsheetmaster;
	}

	public char getOnlineexamstatus() {
		return onlineexamstatus;
	}

	public void setOnlineexamstatus(char onlineexamstatus) {
		this.onlineexamstatus = onlineexamstatus;
	}

}