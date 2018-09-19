package com.igrandee.product.ecloud.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.igrandee.core.login.model.Login;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.core.person.model.Person;


/**
 * The persistent class for the orgperson database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="orgperson")
public class Orgperson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int orgpersonid;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY) 
	@JoinColumn(name="loginid") 
	private Login login;	
 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="organizationid", nullable=false)
	private Organization organizationid; 

	@Column(length=1) 
	private char orgpersonstatus;
 
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY) 
	@JoinColumn(name="personid") 
	private Person personid;


	@OneToMany(cascade=CascadeType.ALL) 
	@JoinColumn(name="orgpersonid")  
	private Set<Personallocation> personallocations;
	
	public Orgperson() {
	}

	public int getOrgpersonid() {
		return this.orgpersonid;
	}

	public void setOrgpersonid(int orgpersonid) {
		this.orgpersonid = orgpersonid;
	}

 	 

	public char getOrgpersonstatus() {
		return orgpersonstatus;
	}

	public void setOrgpersonstatus(char orgpersonstatus) {
		this.orgpersonstatus = orgpersonstatus;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Organization getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(Organization organizationid) {
		this.organizationid = organizationid;
	}

	public Person getPersonid() {
		return personid;
	}

	public void setPersonid(Person personid) {
		this.personid = personid;
	}

	public Set<Personallocation> getPersonallocations() {
		return personallocations;
	}

	public void setPersonallocations(Set<Personallocation> personallocations) {
		this.personallocations = personallocations;
	}

}