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
 * The persistent class for the learningstatistics database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="learningstatistics")
public class Learningstatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int learningstatisticsid;

	@ManyToOne
	@JoinColumn(name="courseenrollmentid", nullable=false)
	private Courseenrollment courseenrollment;
 
 	@ManyToOne
	@JoinColumn(name="courselectureid", nullable=false)
	private Courselecture courselecture;

	@ManyToOne
	@JoinColumn(name="coursesectionid", nullable=false)
	private Coursesection coursesection;
	
	
	@ManyToOne
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;
	
	@Column(length=45)
	private String endtime;

	@Column(length=1)
	private String examstatus;

	@Column(length=45)
	private String markssecured;

	@Column(length=1)
	private String meterialdownloadstatus;

	@Column(length=45)
	private String starttime;

	@Column(length=45)
	private String timespent;
	
	@Column(length=45)
	private String learningstatus;

	@Column
	private Timestamp startdate;
	
	@Column
	private Timestamp completedtime;

	public Learningstatistic() {
	}

	public int getLearningstatisticsid() {
		return this.learningstatisticsid;
	}

	public void setLearningstatisticsid(int learningstatisticsid) {
		this.learningstatisticsid = learningstatisticsid;
	}

 
	public Courseenrollment getCourseenrollment() {
		return courseenrollment;
	}

	public void setCourseenrollment(Courseenrollment courseenrollment) {
		this.courseenrollment = courseenrollment;
	}

	public Courselecture getCourselecture() {
		return courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}

	public Coursesection getCoursesection() {
		return coursesection;
	}

	public void setCoursesection(Coursesection coursesection) {
		this.coursesection = coursesection;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getExamstatus() {
		return this.examstatus;
	}

	public void setExamstatus(String examstatus) {
		this.examstatus = examstatus;
	}

	public String getMarkssecured() {
		return this.markssecured;
	}

	public void setMarkssecured(String markssecured) {
		this.markssecured = markssecured;
	}

	public String getMeterialdownloadstatus() {
		return this.meterialdownloadstatus;
	}

	public void setMeterialdownloadstatus(String meterialdownloadstatus) {
		this.meterialdownloadstatus = meterialdownloadstatus;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getTimespent() {
		return this.timespent;
	}

	public void setTimespent(String timespent) {
		this.timespent = timespent;
	}

	public String getLearningstatus() {
		return learningstatus;
	}

	public void setLearningstatus(String learningstatus) {
		this.learningstatus = learningstatus;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public Timestamp getCompletedtime() {
		return completedtime;
	}

	public void setCompletedtime(Timestamp completedtime) {
		this.completedtime = completedtime;
	}

	public Timestamp getStartdate() {
		return startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

}