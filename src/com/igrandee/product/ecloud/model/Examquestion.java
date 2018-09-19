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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the examquestion database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="examquestion")
public class Examquestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int examquestionid;

	@Column(name="A", length=5000)
	private String a;

	@Column(nullable=false)
	private int actualoccurance;

	@Column(name="B", length=5000)
	private String b;

	@Column(name="C", length=5000)
	private String c;

	@Column(length=5000)
	private String correctans;

	private int countans;

 	@ManyToOne
	@JoinColumn(name="courselectureid", nullable=false)
	private Courselecture courselecture;

	
 	@Column(name="D", length=5000)
	private String d;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofcreation;

	@Column(name="DF", nullable=false, length=45)
	private String df;

	@Column(name="E", length=5000)
	private String e;

	@Temporal(TemporalType.TIMESTAMP)
	private Date eventfromdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date eventtodate;

	@Column(nullable=false, length=1)
	private char examquestionstatus;

	@Column(name="F", length=5000)
	private String f;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fromdate;

	@Column(name="G", length=5000)
	private String g;

	@Column(length=200)
	private String image;

	@Column(nullable=false, length=45)
	private String ipaddress;

	@Column(length=10)
	private String mark;

	@Column(nullable=false)
	private int occurance;

 	
	@ManyToOne
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;

	@Lob
	private String question;

	@Column(length=5)
	private String timelimit;

	@Temporal(TemporalType.TIMESTAMP)
	private Date todate;

	@Column(nullable=false, length=45)
	private String type;

	//bi-directional many-to-one association to Examtype
	@ManyToOne
	@JoinColumn(name="examtypeid")
	private Examtype examtype;

	//bi-directional many-to-one association to Onlineanswer
	@OneToMany(mappedBy="examquestion")
	private Set<Onlineanswer> onlineanswers;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="examquestion")
	private Set<Question> questions;

	public Examquestion() {
	}

	public int getExamquestionid() {
		return this.examquestionid;
	}

	public void setExamquestionid(int examquestionid) {
		this.examquestionid = examquestionid;
	}

	public String getA() {
		return this.a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public int getActualoccurance() {
		return this.actualoccurance;
	}

	public void setActualoccurance(int actualoccurance) {
		this.actualoccurance = actualoccurance;
	}

	public String getB() {
		return this.b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return this.c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getCorrectans() {
		return this.correctans;
	}

	public void setCorrectans(String correctans) {
		this.correctans = correctans;
	}

	public int getCountans() {
		return this.countans;
	}

	public void setCountans(int countans) {
		this.countans = countans;
	}

 	public String getD() {
		return this.d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public Date getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public String getDf() {
		return this.df;
	}

	public void setDf(String df) {
		this.df = df;
	}

	public String getE() {
		return this.e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public Date getEventfromdate() {
		return this.eventfromdate;
	}

	public void setEventfromdate(Date eventfromdate) {
		this.eventfromdate = eventfromdate;
	}

	public Date getEventtodate() {
		return this.eventtodate;
	}

	public void setEventtodate(Date eventtodate) {
		this.eventtodate = eventtodate;
	}
 
	public char getExamquestionstatus() {
		return examquestionstatus;
	}

	public void setExamquestionstatus(char examquestionstatus) {
		this.examquestionstatus = examquestionstatus;
	}
 
	public String getF() {
		return this.f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public Date getFromdate() {
		return this.fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public String getG() {
		return this.g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getOccurance() {
		return this.occurance;
	}

	public void setOccurance(int occurance) {
		this.occurance = occurance;
	}

 	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getTimelimit() {
		return this.timelimit;
	}

	public void setTimelimit(String timelimit) {
		this.timelimit = timelimit;
	}

	public Date getTodate() {
		return this.todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Examtype getExamtype() {
		return this.examtype;
	}

	public void setExamtype(Examtype examtype) {
		this.examtype = examtype;
	}

	public Set<Onlineanswer> getOnlineanswers() {
		return this.onlineanswers;
	}

	public void setOnlineanswers(Set<Onlineanswer> onlineanswers) {
		this.onlineanswers = onlineanswers;
	}

	public Onlineanswer addOnlineanswer(Onlineanswer onlineanswer) {
		getOnlineanswers().add(onlineanswer);
		onlineanswer.setExamquestion(this);

		return onlineanswer;
	}

	public Onlineanswer removeOnlineanswer(Onlineanswer onlineanswer) {
		getOnlineanswers().remove(onlineanswer);
		onlineanswer.setExamquestion(null);

		return onlineanswer;
	}

	public Set<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Question addQuestion(Question question) {
		getQuestions().add(question);
		question.setExamquestion(this);

		return question;
	}

	public Question removeQuestion(Question question) {
		getQuestions().remove(question);
		question.setExamquestion(null);

		return question;
	}

	public Courselecture getCourselecture() {
		return courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

}