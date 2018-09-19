package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the quiz database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="quiz")
public class Quiz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int quizid;

 	@Column(length=200)
	private String quizquestion;
 
 	@Column(length=200)
	private String quiztype;
 	
 	@Column
 	private Timestamp createddate;
 	
	@Column(length=1)
	private String quizstatus;

	//bi-directional many-to-one association to Coursesection
	@ManyToOne
	@JoinColumn(name="courselectureid", nullable=false )
	private Courselecture courselecture;
	
	@OneToMany(mappedBy="quiz",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Quizanswer> quizanswer;
	
	public Quiz() {
	}

	public int getQuizid() {
		return this.quizid;
	}

	public void setQuizid(int quizid) {
		this.quizid = quizid;
	}
  
	public String getQuizquestion() {
		return this.quizquestion;
	}

	public void setQuizquestion(String quizquestion) {
		this.quizquestion = quizquestion;
	}

	public String getQuizstatus() {
		return this.quizstatus;
	}

	public void setQuizstatus(String quizstatus) {
		this.quizstatus = quizstatus;
	}

	public Courselecture getCourselecture() {
		return courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}
	
	public String getQuiztype() {
		return quiztype;
	}

	public void setQuiztype(String quiztype) {
		this.quiztype = quiztype;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public Set<Quizanswer> getQuizanswer() {
		return quizanswer;
	}

	public void setQuizanswer(Set<Quizanswer> quizanswer) {
		this.quizanswer = quizanswer;
	}
  
}