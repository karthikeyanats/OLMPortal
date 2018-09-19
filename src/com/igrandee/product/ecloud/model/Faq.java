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
 * The persistent class for the faq database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="faq")
public class Faq implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int FAQid;
 

	@Column(length=500)
	private String FAQquestion;

	@Column(length=1)
	private String FAQstatus;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courselectureid", nullable=false)
	private Courselecture courselecture;

	@OneToMany(mappedBy="faq",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Faqanswer> faqanswer;
	
	@ManyToOne
	@JoinColumn(name="askedby", nullable=false)
	private Orgperson askedby;
	 
	@Column
 	private Timestamp askeddate;
	
	public Faq() {
	}

	public int getFAQid() {
		return this.FAQid;
	}

	public void setFAQid(int FAQid) {
		this.FAQid = FAQid;
	}

 
	public String getFAQquestion() {
		return this.FAQquestion;
	}

	public void setFAQquestion(String FAQquestion) {
		this.FAQquestion = FAQquestion;
	}

	public String getFAQstatus() {
		return this.FAQstatus;
	}

	public void setFAQstatus(String FAQstatus) {
		this.FAQstatus = FAQstatus;
	}

	public Courselecture getCourselecture() {
		return courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}

	public Set<Faqanswer> getFaqanswer() {
		return faqanswer;
	}

	public void setFaqanswer(Set<Faqanswer> faqanswer) {
		this.faqanswer = faqanswer;
	}

	public Orgperson getAskedby() {
		return askedby;
	}

	public void setAskedby(Orgperson askedby) {
		this.askedby = askedby;
	}

	public Timestamp getAskeddate() {
		return askeddate;
	}

	public void setAskeddate(Timestamp askeddate) {
		this.askeddate = askeddate;
	}
 
}