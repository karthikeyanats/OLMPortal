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
 * The persistent class for the faqanswer database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="faqanswer")
public class Faqanswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int faqanswerid;

	@Column(length=500)
	private String faqanswer;

	@Column(length=3)
	private String faqanswerstatus;

 
	@ManyToOne
	@JoinColumn(name="FAQid", nullable=false)
	private Faq faq;
	
	@ManyToOne
	@JoinColumn(name="answeredby", nullable=false)
	private Orgperson answeredby;
	
	@Column
 	private Timestamp answereddate;
 
	public Faqanswer() {
	}

	public int getFaqanswerid() {
		return this.faqanswerid;
	}

	public void setFaqanswerid(int faqanswerid) {
		this.faqanswerid = faqanswerid;
	}

	public String getFaqanswer() {
		return this.faqanswer;
	}

	public void setFaqanswer(String faqanswer) {
		this.faqanswer = faqanswer;
	}

	public String getFaqanswerstatus() {
		return this.faqanswerstatus;
	}

	public void setFaqanswerstatus(String faqanswerstatus) {
		this.faqanswerstatus = faqanswerstatus;
	}

 	public Faq getFaq() {
		return faq;
	}

	public void setFaq(Faq faq) {
		this.faq = faq;
	}

	public Orgperson getAnsweredby() {
		return answeredby;
	}

	public void setAnsweredby(Orgperson answeredby) {
		this.answeredby = answeredby;
	}

	public Timestamp getAnswereddate() {
		return answereddate;
	}

	public void setAnswereddate(Timestamp answereddate) {
		this.answereddate = answereddate;
	}

}