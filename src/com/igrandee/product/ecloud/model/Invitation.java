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
 * The persistent class for the invitation database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="invitation")
public class Invitation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int invitationid;

	@Column(length=200)
	private String invitationmessage;

	@Column(length=1)
	private String invitationstatus;

	//bi-directional many-to-one association to Organizationcourse
	@ManyToOne
	@JoinColumn(name="organizationcourseid")
	private Organizationcourse organizationcourse1;

	//bi-directional many-to-one association to Organizationcourse
	@ManyToOne
	@JoinColumn(name="organizationcourse_organizationcourseid", nullable=false)
	private Organizationcourse organizationcourse2;

	//bi-directional many-to-one association to Organizationuser
	@ManyToOne
	@JoinColumn(name="organizationuserid")
	private Organizationuser organizationuser1;

	//bi-directional many-to-one association to Organizationuser
	@ManyToOne
	@JoinColumn(name="organizationuser_organizationuserid", nullable=false)
	private Organizationuser organizationuser2;

	public Invitation() {
	}

	public int getInvitationid() {
		return this.invitationid;
	}

	public void setInvitationid(int invitationid) {
		this.invitationid = invitationid;
	}

	public String getInvitationmessage() {
		return this.invitationmessage;
	}

	public void setInvitationmessage(String invitationmessage) {
		this.invitationmessage = invitationmessage;
	}

	public String getInvitationstatus() {
		return this.invitationstatus;
	}

	public void setInvitationstatus(String invitationstatus) {
		this.invitationstatus = invitationstatus;
	}

	public Organizationcourse getOrganizationcourse1() {
		return this.organizationcourse1;
	}

	public void setOrganizationcourse1(Organizationcourse organizationcourse1) {
		this.organizationcourse1 = organizationcourse1;
	}

	public Organizationcourse getOrganizationcourse2() {
		return this.organizationcourse2;
	}

	public void setOrganizationcourse2(Organizationcourse organizationcourse2) {
		this.organizationcourse2 = organizationcourse2;
	}

	public Organizationuser getOrganizationuser1() {
		return this.organizationuser1;
	}

	public void setOrganizationuser1(Organizationuser organizationuser1) {
		this.organizationuser1 = organizationuser1;
	}

	public Organizationuser getOrganizationuser2() {
		return this.organizationuser2;
	}

	public void setOrganizationuser2(Organizationuser organizationuser2) {
		this.organizationuser2 = organizationuser2;
	}

}