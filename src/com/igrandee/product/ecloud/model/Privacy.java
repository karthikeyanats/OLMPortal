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
 * The persistent class for the privacy database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="privacy")
public class Privacy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int privacyid;

	@Column(length=100)
	private String privacydescription;

	@Column(length=45)
	private String privacyname;

	@Column(length=1)
	private String privacystatus;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course course;

	public Privacy() {
	}

	public int getPrivacyid() {
		return this.privacyid;
	}

	public void setPrivacyid(int privacyid) {
		this.privacyid = privacyid;
	}

	public String getPrivacydescription() {
		return this.privacydescription;
	}

	public void setPrivacydescription(String privacydescription) {
		this.privacydescription = privacydescription;
	}

	public String getPrivacyname() {
		return this.privacyname;
	}

	public void setPrivacyname(String privacyname) {
		this.privacyname = privacyname;
	}

	public String getPrivacystatus() {
		return this.privacystatus;
	}

	public void setPrivacystatus(String privacystatus) {
		this.privacystatus = privacystatus;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}