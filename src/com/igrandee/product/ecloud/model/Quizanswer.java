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
 * The persistent class for the quizanswer database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="quizanswer")
public class Quizanswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int quizanswerid;

	@Column(length=500)
	private String quizanswer;

	@ManyToOne
	@JoinColumn(name="quizid", nullable=false)
	private Quiz quiz;
	
	
	@Column(length=45)
	private String quizstatus;

	public Quizanswer() {
	}

	public int getQuizanswerid() {
		return this.quizanswerid;
	}

	public void setQuizanswerid(int quizanswerid) {
		this.quizanswerid = quizanswerid;
	}

	public String getQuizanswer() {
		return this.quizanswer;
	}

	public void setQuizanswer(String quizanswer) {
		this.quizanswer = quizanswer;
	}
  
	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public String getQuizstatus() {
		return this.quizstatus;
	}

	public void setQuizstatus(String quizstatus) {
		this.quizstatus = quizstatus;
	}

}