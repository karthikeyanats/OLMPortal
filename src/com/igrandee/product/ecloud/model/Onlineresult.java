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


/**
 * The persistent class for the onlineresult database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="onlineresult")
public class Onlineresult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int onlineresultid; 

	private int createdby;

	@Column(nullable=false, length=1)
	private char currentstatus;

	@Column(nullable=false)
	private Timestamp dateofcreation;

	@Column(nullable=false, length=45)
	private String obtainedmarks;

	@ManyToOne
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;
	
	//bi-directional many-to-one association to Questionsheetmaster
	@ManyToOne
	@JoinColumn(name="qsid")
	private Questionsheetmaster questionsheetmaster;

	public Onlineresult() {
	}
 	 
	public int getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(int createdby) {
		this.createdby = createdby;
	}
 
	public Timestamp getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Timestamp dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public String getObtainedmarks() {
		return this.obtainedmarks;
	}

	public void setObtainedmarks(String obtainedmarks) {
		this.obtainedmarks = obtainedmarks;
	}
 
	public Questionsheetmaster getQuestionsheetmaster() {
		return this.questionsheetmaster;
	}

	public void setQuestionsheetmaster(Questionsheetmaster questionsheetmaster) {
		this.questionsheetmaster = questionsheetmaster;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public char getCurrentstatus() {
		return currentstatus;
	}

	public void setCurrentstatus(char currentstatus) {
		this.currentstatus = currentstatus;
	}

	public int getOnlineresultid() {
		return onlineresultid;
	}

	public void setOnlineresultid(int onlineresultid) {
		this.onlineresultid = onlineresultid;
	}

}