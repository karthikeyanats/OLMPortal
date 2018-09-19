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
 * The persistent class for the onlineanswers database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="onlineanswers")
public class Onlineanswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int answerid;

	@Column(length=45)
	private String answer;

	@Column(length=45)
	private String attempt;

	@Column(nullable=false)
	private Timestamp dateofcreation;

	@ManyToOne
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;
	
	@Column(length=10)
	private String timespent;

	//bi-directional many-to-one association to Questionsheetmaster
	@ManyToOne
	@JoinColumn(name="qsid")
	private Questionsheetmaster questionsheetmaster;

	//bi-directional many-to-one association to Examquestion
	@ManyToOne
	@JoinColumn(name="examquestionid")
	private Examquestion examquestion;

	public Onlineanswer() {
	}

	public int getAnswerid() {
		return this.answerid;
	}

	public void setAnswerid(int answerid) {
		this.answerid = answerid;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAttempt() {
		return this.attempt;
	}

	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}

	public Timestamp getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Timestamp dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	 
	public String getTimespent() {
		return this.timespent;
	}

	public void setTimespent(String timespent) {
		this.timespent = timespent;
	}

	public Questionsheetmaster getQuestionsheetmaster() {
		return this.questionsheetmaster;
	}

	public void setQuestionsheetmaster(Questionsheetmaster questionsheetmaster) {
		this.questionsheetmaster = questionsheetmaster;
	}

	public Examquestion getExamquestion() {
		return this.examquestion;
	}

	public void setExamquestion(Examquestion examquestion) {
		this.examquestion = examquestion;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

}