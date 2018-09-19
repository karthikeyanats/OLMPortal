package com.igrandee.product.ecloud.model.sat;

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

import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.satutil.model.SatQuestion;


/**
 * The persistent class for the evaluation database table.
 * 
 */
@Entity
@Table(name="evaluation")  
public class Evaluation implements Serializable {  
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=1)
	private char answerstate;
	
	@Column(length=45)
	private String takentime;

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofcreation;

	@ManyToOne
	@JoinColumn(name="orgpersonid")
	private Orgperson orgperson;

	@ManyToOne 
	@JoinColumn(name="questionid", nullable=false)
	private SatQuestion question;
	
	@Column
	private int studentanswer;
	
	@Column
	private int answerid;
	
	@Column
	private int attempt;
	
	/*@ManyToOne
	@JoinColumn(name="courseenrollmentid")
	private Courseenrollment courseenrollmentid;*/
	

	/**
	 * @return the answerid
	 */
	public int getAnswerid() {
		return answerid;
	}

	/**
	 * @param answerid the answerid to set
	 */
	public void setAnswerid(int answerid) {
		this.answerid = answerid;
	}

	/**
	 * @return the attempt
	 */
	public int getAttempt() {
		return attempt;
	}

	/**
	 * @param attempt the attempt to set
	 */
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public Evaluation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char getAnswerstate() {
		return this.answerstate;
	}

	public void setAnswerstate(char answerstate) {
		this.answerstate = answerstate;
	}
	
	/**
	 * @return the takentime
	 */
	public String getTakentime() {
		return takentime;
	}

	/**
	 * @param takentime the takentime to set
	 */
	public void setTakentime(String takentime) {
		this.takentime = takentime;
	}

	public Date getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}
 	 
	/**
	 * @return the orgperson
	 */
	public Orgperson getOrgperson() {
		return orgperson;
	}

	/**
	 * @param orgperson the orgperson to set
	 */
	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	/**
	 * @return the question
	 */
	public SatQuestion getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(SatQuestion question) {
		this.question = question;
	}

	public int getStudentanswer() {
		return this.studentanswer;
	}

	public void setStudentanswer(int studentanswer) {
		this.studentanswer = studentanswer;
	}

}