package com.igrandee.product.ecloud.model;

import java.io.Serializable;
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
 * The persistent class for the organizationcourse database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="organizationcourse")
public class Organizationcourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int organizationcourseid;

	@Column(length=45)
	private String organizationcoursestatus;

	@Column(length=45)
	private String organizationcourseurl;

	private int organizationid;

	//bi-directional many-to-one association to Invitation
	@OneToMany(mappedBy="organizationcourse1")
	private Set<Invitation> invitations1;

	//bi-directional many-to-one association to Invitation
	@OneToMany(mappedBy="organizationcourse2")
	private Set<Invitation> invitations2;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course course;

	public Organizationcourse() {
	}

	public int getOrganizationcourseid() {
		return this.organizationcourseid;
	}

	public void setOrganizationcourseid(int organizationcourseid) {
		this.organizationcourseid = organizationcourseid;
	}

	public String getOrganizationcoursestatus() {
		return this.organizationcoursestatus;
	}

	public void setOrganizationcoursestatus(String organizationcoursestatus) {
		this.organizationcoursestatus = organizationcoursestatus;
	}

	public String getOrganizationcourseurl() {
		return this.organizationcourseurl;
	}

	public void setOrganizationcourseurl(String organizationcourseurl) {
		this.organizationcourseurl = organizationcourseurl;
	}

	public int getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(int organizationid) {
		this.organizationid = organizationid;
	}

	public Set<Invitation> getInvitations1() {
		return this.invitations1;
	}

	public void setInvitations1(Set<Invitation> invitations1) {
		this.invitations1 = invitations1;
	}

	public Invitation addInvitations1(Invitation invitations1) {
		getInvitations1().add(invitations1);
		invitations1.setOrganizationcourse1(this);

		return invitations1;
	}

	public Invitation removeInvitations1(Invitation invitations1) {
		getInvitations1().remove(invitations1);
		invitations1.setOrganizationcourse1(null);

		return invitations1;
	}

	public Set<Invitation> getInvitations2() {
		return this.invitations2;
	}

	public void setInvitations2(Set<Invitation> invitations2) {
		this.invitations2 = invitations2;
	}

	public Invitation addInvitations2(Invitation invitations2) {
		getInvitations2().add(invitations2);
		invitations2.setOrganizationcourse2(this);

		return invitations2;
	}

	public Invitation removeInvitations2(Invitation invitations2) {
		getInvitations2().remove(invitations2);
		invitations2.setOrganizationcourse2(null);

		return invitations2;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}