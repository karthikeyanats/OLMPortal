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

@Repository
@Scope("prototype")
@Entity
@Table(name="lecturereview")
public class LectureReview implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int lecturereviewid;
	
	@Column(length=45)
	private String rank;
	
	@Column(length=50)
	private Timestamp dateofcreation;
	
	@Column(length=1)
	private char lecturereviewstatus;

	@ManyToOne
	@JoinColumn(name="lectureid", nullable=false)
	private Courselecture courselecture;
	
	@ManyToOne
	@JoinColumn(name="reviewtypeid", nullable=false)
	private Reviewtype reviewtype;
	
	@ManyToOne
	@JoinColumn(name="userid", nullable=false)
	private Orgperson orgperson;

	public int getLecturereviewid() {
		return lecturereviewid;
	}

	public void setLecturereviewid(int lecturereviewid) {
		this.lecturereviewid = lecturereviewid;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Timestamp getDateofcreation() {
		return dateofcreation;
	}

	public void setDateofcreation(Timestamp dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	

	

	public char getLecturereviewstatus() {
		return lecturereviewstatus;
	}

	public void setLecturereviewstatus(char lecturereviewstatus) {
		this.lecturereviewstatus = lecturereviewstatus;
	}

	public Courselecture getCourselecture() {
		return courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}

	public Reviewtype getReviewtype() {
		return reviewtype;
	}

	public void setReviewtype(Reviewtype reviewtype) {
		this.reviewtype = reviewtype;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}
	
	
	
	
}
