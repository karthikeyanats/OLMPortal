package com.igrandee.product.ecloud.model.sat;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.satutil.model.SatQuestion;


/**
 * The persistent class for the questionconnector database table.
 * 
 */
@Entity
@Table(name="questionconnector")
public class Questionconnector implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="courselectureid", nullable=false)
	private Courselecture courselecture;
 
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofcreation;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER) 
	@JoinColumn(name="question_id", nullable=false)
	private SatQuestion question;
 	
	private char status;

	public Questionconnector() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	/**
	 * @return the courselecture
	 */
	public Courselecture getCourselecture() {
		return courselecture;
	}

	/**
	 * @param courselecture the courselecture to set
	 */
	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
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

}