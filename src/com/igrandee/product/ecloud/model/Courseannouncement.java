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
 * The persistent class for the courseannouncement database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="courseannouncement")
public class Courseannouncement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int courseannouncementid;

	@Column(length=300)
	private String courseannouncementdescription;

	private Timestamp courseannouncementenddate;

	private Timestamp courseannouncementstartdate;

	@Column(length=1)
	private String courseannouncementstatus;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course course;

	public Courseannouncement() {
	}

	public int getCourseannouncementid() {
		return this.courseannouncementid;
	}

	public void setCourseannouncementid(int courseannouncementid) {
		this.courseannouncementid = courseannouncementid;
	}

	public String getCourseannouncementdescription() {
		return this.courseannouncementdescription;
	}

	public void setCourseannouncementdescription(String courseannouncementdescription) {
		this.courseannouncementdescription = courseannouncementdescription;
	}

	public Timestamp getCourseannouncementenddate() {
		return this.courseannouncementenddate;
	}

	public void setCourseannouncementenddate(Timestamp courseannouncementenddate) {
		this.courseannouncementenddate = courseannouncementenddate;
	}

	public Timestamp getCourseannouncementstartdate() {
		return this.courseannouncementstartdate;
	}

	public void setCourseannouncementstartdate(Timestamp courseannouncementstartdate) {
		this.courseannouncementstartdate = courseannouncementstartdate;
	}

	public String getCourseannouncementstatus() {
		return this.courseannouncementstatus;
	}

	public void setCourseannouncementstatus(String courseannouncementstatus) {
		this.courseannouncementstatus = courseannouncementstatus;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}