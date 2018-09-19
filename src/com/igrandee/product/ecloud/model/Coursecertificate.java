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
 * The persistent class for the coursecertificate database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="coursecertificate")
public class Coursecertificate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int coursecertificateid;

	@Column(length=100)
	private String certificatedescription;

	@Column(length=1)
	private String coursecertificatestatus;
	
	@Column
	private Timestamp dateofissue;

 	//bi-directional many-to-one association to Orgperson
	@ManyToOne
	@JoinColumn(name="courseenrollmentid", nullable=false)
	private Courseenrollment courseenrollment;

	public Coursecertificate() {
	}

	public int getCoursecertificateid() {
		return this.coursecertificateid;
	}

	public void setCoursecertificateid(int coursecertificateid) {
		this.coursecertificateid = coursecertificateid;
	}

	public String getCertificatedescription() {
		return this.certificatedescription;
	}

	public void setCertificatedescription(String certificatedescription) {
		this.certificatedescription = certificatedescription;
	}

	public String getCoursecertificatestatus() {
		return this.coursecertificatestatus;
	}

	public void setCoursecertificatestatus(String coursecertificatestatus) {
		this.coursecertificatestatus = coursecertificatestatus;
	}

	public Courseenrollment getCourseenrollment() {
		return courseenrollment;
	}

	public void setCourseenrollment(Courseenrollment courseenrollment) {
		this.courseenrollment = courseenrollment;
	}

	public Timestamp getDateofissue() {
		return dateofissue;
	}

	public void setDateofissue(Timestamp dateofissue) {
		this.dateofissue = dateofissue;
	}

}