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
 * The persistent class for the courserating database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="courserating")
public class Courserating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int courseratingid;

	@Column(length=45)
	private String courserating;

	@Column(length=500)
	private String courseratingdescription;

	@Column(length=1)
	private String courseratingstatus;
	
	@Column(length=50)
	private Timestamp ratingdate;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course course;

	@ManyToOne
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;
	
	public Courserating() {
	}

	public int getCourseratingid() {
		return this.courseratingid;
	}

	public void setCourseratingid(int courseratingid) {
		this.courseratingid = courseratingid;
	}

	public String getCourserating() {
		return this.courserating;
	}

	public void setCourserating(String courserating) {
		this.courserating = courserating;
	}

	public String getCourseratingdescription() {
		return this.courseratingdescription;
	}

	public void setCourseratingdescription(String courseratingdescription) {
		this.courseratingdescription = courseratingdescription;
	}

	public String getCourseratingstatus() {
		return this.courseratingstatus;
	}

	public void setCourseratingstatus(String courseratingstatus) {
		this.courseratingstatus = courseratingstatus;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public Timestamp getRatingdate() {
		return ratingdate;
	}

	public void setRatingdate(Timestamp ratingdate) {
		this.ratingdate = ratingdate;
	}

}