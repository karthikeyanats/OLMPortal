package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the coursesection database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="giftcourse")
public class GiftCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int giftcourseid;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course course;
	
	@Column(length=50)
	private String recipientname;
	
	@Column(length=200)
	private String recipientmail;

	@Column
	private Timestamp senddate;
	
	@Column(length=500)
	private String message;

	@Column(length=1)
	private String giftcoursestatus;  
	
	@ManyToOne
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;
	
	
	@ManyToOne
	@JoinColumn(name="beneficiaryid", nullable=false)
	private Orgperson beneficiary;
	
	
	//bi-directional one-to-many association to Payments
	@OneToMany(mappedBy="giftcourse")
	private Set<Payment> payments;
	
	public GiftCourse() {
	}


	public int getGiftcourseid() {
		return giftcourseid;
	}

 	public void setGiftcourseid(int giftcourseid) {
		this.giftcourseid = giftcourseid;
	}


	public Course getCourse() {
		return course;
	}


	public void setCourse(Course course) {
		this.course = course;
	}


	public String getRecipientname() {
		return recipientname;
	}


	public void setRecipientname(String recipientname) {
		this.recipientname = recipientname;
	}


	public String getRecipientmail() {
		return recipientmail;
	}


	public void setRecipientmail(String recipientmail) {
		this.recipientmail = recipientmail;
	}


	public Timestamp getSenddate() {
		return senddate;
	}


	public void setSenddate(Timestamp senddate) {
		this.senddate = senddate;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getGiftcoursestatus() {
		return giftcoursestatus;
	}


	public void setGiftcoursestatus(String giftcoursestatus) {
		this.giftcoursestatus = giftcoursestatus;
	}


	public Orgperson getOrgperson() {
		return orgperson;
	}


	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}


	public Orgperson getBeneficiary() {
		return beneficiary;
	}


	public void setBeneficiaryid(Orgperson beneficiary) {
		this.beneficiary= beneficiary;
	}
 
}