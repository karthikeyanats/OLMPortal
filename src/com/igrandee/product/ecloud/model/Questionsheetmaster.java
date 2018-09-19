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
 * The persistent class for the questionsheetmaster database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="questionsheetmaster")
public class Questionsheetmaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int qsid;

	@Column(length=45)
	private String attempt;

 	@ManyToOne
	@JoinColumn(name="coursesectionid", nullable=false)
	private Coursesection coursesection;
 
	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;

	@Column(length=45)
	private String duration;

	@Column(length=500)
	private String examname; 

	@Column(columnDefinition="TEXT")
	private String instruction;

	@Column(nullable=false, length=1)
	private char questionsheetstatus;

	@Column(length=45)
	private String restartingduration;

	@Column(length=45)
	private String templatesubjecttype;

	@Column(nullable=false, length=45)
	private String templatetype;

	//bi-directional many-to-one association to Onlineanswer
	@OneToMany(mappedBy="questionsheetmaster")
	private Set<Onlineanswer> onlineanswers;

	//bi-directional many-to-one association to Onlineexam
	@OneToMany(mappedBy="questionsheetmaster")
	private Set<Onlineexam> onlineexams;

	//bi-directional many-to-one association to Onlineresult
	@OneToMany(mappedBy="questionsheetmaster")
	private Set<Onlineresult> onlineresults;

	//bi-directional many-to-one association to Questionsheetdetail
	@OneToMany(mappedBy="questionsheetmaster")
	private Set<Questionsheetdetail> questionsheetdetails;

	public Questionsheetmaster() {
	}

	public int getQsid() {
		return this.qsid;
	}

	public void setQsid(int qsid) {
		this.qsid = qsid;
	}

	public String getAttempt() {
		return this.attempt;
	}

	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}
 
	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
 
	public String getRestartingduration() {
		return this.restartingduration;
	}

	public void setRestartingduration(String restartingduration) {
		this.restartingduration = restartingduration;
	}

	public String getTemplatesubjecttype() {
		return this.templatesubjecttype;
	}

	public void setTemplatesubjecttype(String templatesubjecttype) {
		this.templatesubjecttype = templatesubjecttype;
	}

	public String getTemplatetype() {
		return this.templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;
	}

	public Set<Onlineanswer> getOnlineanswers() {
		return this.onlineanswers;
	}

	public void setOnlineanswers(Set<Onlineanswer> onlineanswers) {
		this.onlineanswers = onlineanswers;
	}

	public Onlineanswer addOnlineanswer(Onlineanswer onlineanswer) {
		getOnlineanswers().add(onlineanswer);
		onlineanswer.setQuestionsheetmaster(this);

		return onlineanswer;
	}

	public Onlineanswer removeOnlineanswer(Onlineanswer onlineanswer) {
		getOnlineanswers().remove(onlineanswer);
		onlineanswer.setQuestionsheetmaster(null);

		return onlineanswer;
	}

	public Set<Onlineexam> getOnlineexams() {
		return this.onlineexams;
	}

	public void setOnlineexams(Set<Onlineexam> onlineexams) {
		this.onlineexams = onlineexams;
	}

	public Onlineexam addOnlineexam(Onlineexam onlineexam) {
		getOnlineexams().add(onlineexam);
		onlineexam.setQuestionsheetmaster(this);

		return onlineexam;
	}

	public Onlineexam removeOnlineexam(Onlineexam onlineexam) {
		getOnlineexams().remove(onlineexam);
		onlineexam.setQuestionsheetmaster(null);

		return onlineexam;
	}

	public Set<Onlineresult> getOnlineresults() {
		return this.onlineresults;
	}

	public void setOnlineresults(Set<Onlineresult> onlineresults) {
		this.onlineresults = onlineresults;
	}

	public Onlineresult addOnlineresult(Onlineresult onlineresult) {
		getOnlineresults().add(onlineresult);
		onlineresult.setQuestionsheetmaster(this);

		return onlineresult;
	}

	public Onlineresult removeOnlineresult(Onlineresult onlineresult) {
		getOnlineresults().remove(onlineresult);
		onlineresult.setQuestionsheetmaster(null);

		return onlineresult;
	}

	public Set<Questionsheetdetail> getQuestionsheetdetails() {
		return this.questionsheetdetails;
	}

	public void setQuestionsheetdetails(Set<Questionsheetdetail> questionsheetdetails) {
		this.questionsheetdetails = questionsheetdetails;
	}

	public Questionsheetdetail addQuestionsheetdetail(Questionsheetdetail questionsheetdetail) {
		getQuestionsheetdetails().add(questionsheetdetail);
		questionsheetdetail.setQuestionsheetmaster(this);

		return questionsheetdetail;
	}

	public Questionsheetdetail removeQuestionsheetdetail(Questionsheetdetail questionsheetdetail) {
		getQuestionsheetdetails().remove(questionsheetdetail);
		questionsheetdetail.setQuestionsheetmaster(null);

		return questionsheetdetail;
	}

	public Coursesection getCoursesection() {
		return coursesection;
	}

	public void setCoursesection(Coursesection coursesection) {
		this.coursesection = coursesection;
	}

	public char getQuestionsheetstatus() {
		return questionsheetstatus;
	}

	public void setQuestionsheetstatus(char questionsheetstatus) {
		this.questionsheetstatus = questionsheetstatus;
	}

}