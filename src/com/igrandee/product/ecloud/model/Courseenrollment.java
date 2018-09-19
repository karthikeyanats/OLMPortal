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
 * The persistent class for the courseenrollment database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="courseenrollment")
public class Courseenrollment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int courseenrollmentid;

	@Column(length=10)
	private String courseenrollmentstatus;
	
	@Column
	private Timestamp enrolleddate;
	
	@Column
	private Timestamp completeddate;

	//bi-directional many-to-one association to Course
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseid", nullable=false)
	private Course course;

	@Column(length=45)
	private String courseprogress;

	
	@OneToMany(mappedBy="courseenrollment")
	private Set<Learningstatistic> learningstatistic;
	 
	@OneToMany(mappedBy="courseenrollment",cascade=CascadeType.ALL)
	private Set<Payment> payment;
	
 	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="personid", nullable=false)
	private Orgperson orgperson;
	
	

	public Courseenrollment() {
	}

	public int getCourseenrollmentid() {
		return this.courseenrollmentid;
	}

	public void setCourseenrollmentid(int courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}

	public String getCourseenrollmentstatus() {
		return this.courseenrollmentstatus;
	}

	public void setCourseenrollmentstatus(String courseenrollmentstatus) {
		this.courseenrollmentstatus = courseenrollmentstatus;
	}

 	public String getCourseprogress() {
		return this.courseprogress;
	}

	public void setCourseprogress(String courseprogress) {
		this.courseprogress = courseprogress;
	}
 
	public Course getCourse() {
		return course;
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

	public Set<Payment> getPayment() {
		return payment;
	}

	public void setPayment(Set<Payment> payment) {
		this.payment = payment;
	}

	public Timestamp getEnrolleddate() {
		return enrolleddate;
	}

	public void setEnrolleddate(Timestamp enrolleddate) {
		this.enrolleddate = enrolleddate;
	}

	public Timestamp getCompleteddate() {
		return completeddate;
	}

	public void setCompleteddate(Timestamp completeddate) {
		this.completeddate = completeddate;
	}
	
}