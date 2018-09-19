package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the questionsheetdetail database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="questionsheetdetail")
public class Questionsheetdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int qsdid;

	@ManyToOne
	@JoinColumn(name="courselectureid", nullable=false)
	private Courselecture courselecture;
	
	@Column(nullable=false, length=500)
	private String df;

	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;

	@Column(nullable=false)
	private int marksperqn;

	@Column(nullable=false)
	private int noqn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;

	@Column(nullable=false, length=500)
	private String type;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="questionsheetdetail")
	private Set<Question> questions;

	//bi-directional many-to-one association to Questionsheetmaster
	@ManyToOne
	@JoinColumn(name="qsid")
	private Questionsheetmaster questionsheetmaster;

	public Questionsheetdetail() {
	}

	public int getQsdid() {
		return this.qsdid;
	}

	public void setQsdid(int qsdid) {
		this.qsdid = qsdid;
	}
 
	public String getDf() {
		return this.df;
	}

	public void setDf(String df) {
		this.df = df;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
 	 
	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Question addQuestion(Question question) {
		getQuestions().add(question);
		question.setQuestionsheetdetail(this);

		return question;
	}

	public Question removeQuestion(Question question) {
		getQuestions().remove(question);
		question.setQuestionsheetdetail(null);

		return question;
	}

	public Questionsheetmaster getQuestionsheetmaster() {
		return this.questionsheetmaster;
	}

	public void setQuestionsheetmaster(Questionsheetmaster questionsheetmaster) {
		this.questionsheetmaster = questionsheetmaster;
	}

	public Courselecture getCourselecture() {
		return courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}

	public int getMarksperqn() {
		return marksperqn;
	}

	public void setMarksperqn(int marksperqn) {
		this.marksperqn = marksperqn;
	}

	public int getNoqn() {
		return noqn;
	}

	public void setNoqn(int noqn) {
		this.noqn = noqn;
	}

}