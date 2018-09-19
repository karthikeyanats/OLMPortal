package com.igrandee.product.ecloud.model;

import java.io.Serializable;

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
 * The persistent class for the questions database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="questions")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int questionid;

	@Column(nullable=false)
	private int serialnumber;

	//bi-directional many-to-one association to Examquestion
	@ManyToOne
	@JoinColumn(name="examquestionid", nullable=false)
	private Examquestion examquestion;

	//bi-directional many-to-one association to Questionsheetdetail
	@ManyToOne
	@JoinColumn(name="qsdid", nullable=false)
	private Questionsheetdetail questionsheetdetail;

	public Question() {
	}
  
	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

	public int getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(int serialnumber) {
		this.serialnumber = serialnumber;
	}

	public Examquestion getExamquestion() {
		return this.examquestion;
	}

	public void setExamquestion(Examquestion examquestion) {
		this.examquestion = examquestion;
	}

	public Questionsheetdetail getQuestionsheetdetail() {
		return this.questionsheetdetail;
	}

	public void setQuestionsheetdetail(Questionsheetdetail questionsheetdetail) {
		this.questionsheetdetail = questionsheetdetail;
	}

}