package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the organizationuser database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="organizationuser")
public class Organizationuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int organizationuserid;

	private int organizationid;

	@Column(length=1)
	private String organizationuserstatus;

	private int personid;

	//bi-directional many-to-one association to Invitation
	@OneToMany(mappedBy="organizationuser1")
	private Set<Invitation> invitations1;

	//bi-directional many-to-one association to Invitation
	@OneToMany(mappedBy="organizationuser2")
	private Set<Invitation> invitations2;

	public Organizationuser() {
	}

	public int getOrganizationuserid() {
		return this.organizationuserid;
	}

	public void setOrganizationuserid(int organizationuserid) {
		this.organizationuserid = organizationuserid;
	}

	public int getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(int organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrganizationuserstatus() {
		return this.organizationuserstatus;
	}

	public void setOrganizationuserstatus(String organizationuserstatus) {
		this.organizationuserstatus = organizationuserstatus;
	}

	public int getPersonid() {
		return this.personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

	public Set<Invitation> getInvitations1() {
		return this.invitations1;
	}

	public void setInvitations1(Set<Invitation> invitations1) {
		this.invitations1 = invitations1;
	}

	public Invitation addInvitations1(Invitation invitations1) {
		getInvitations1().add(invitations1);
		invitations1.setOrganizationuser1(this);

		return invitations1;
	}

	public Invitation removeInvitations1(Invitation invitations1) {
		getInvitations1().remove(invitations1);
		invitations1.setOrganizationuser1(null);

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
		invitations2.setOrganizationuser2(this);

		return invitations2;
	}

	public Invitation removeInvitations2(Invitation invitations2) {
		getInvitations2().remove(invitations2);
		invitations2.setOrganizationuser2(null);

		return invitations2;
	}

}