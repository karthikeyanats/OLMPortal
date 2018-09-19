package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the courseinvitation database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="courseinvitation")
public class Courseinvitation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int courseinvitationid;

 	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course course;

	@Column(length=1)
	private char courseinvitationstatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofinvitation;

	@Column(length=200)
	private String emailid;
	
	@Column(length=500)
	private String message;
	
	@Column(length=200)
	private String attachmentpath;	

 	@ManyToOne
	@JoinColumn(name="invitedby", nullable=false)
	private Orgperson orgperson;
 	
 	//bi-directional many-to-one association to Orgplanuser
 	@OneToMany(mappedBy="courseinvitation",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
 	private Set<Orgplanuser> orgplanusers;

	public Courseinvitation() {
	}

	public int getCourseinvitationid() {
		return this.courseinvitationid;
	}

	public void setCourseinvitationid(int courseinvitationid) {
		this.courseinvitationid = courseinvitationid;
	}
 	 
	public char getCourseinvitationstatus() {
		return this.courseinvitationstatus;
	}

	public void setCourseinvitationstatus(char courseinvitationstatus) {
		this.courseinvitationstatus = courseinvitationstatus;
	}

	public Date getDateofinvitation() {
		return this.dateofinvitation;
	}

	public void setDateofinvitation(Date dateofinvitation) {
		this.dateofinvitation = dateofinvitation;
	}

	public String getEmailid() {
		return this.emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAttachmentpath() {
		return attachmentpath;
	}

	public void setAttachmentpath(String attachmentpath) {
		this.attachmentpath = attachmentpath;
	}
	
	public Set<Orgplanuser> getOrgplanusers() {
		return this.orgplanusers;
	}

	public void setOrgplanusers(Set<Orgplanuser> orgplanusers) {
		this.orgplanusers = orgplanusers;
	}
 	 
}