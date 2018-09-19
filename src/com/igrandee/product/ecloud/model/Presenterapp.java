package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
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
 * The persistent class for the presenterapp database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="presenterapp")
public class Presenterapp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int presenterid;

	@Column(length=45)
	private String applicationname;

	@Column(length=250)
	private String apppath;

 	@ManyToOne
	@JoinColumn(name="createdby", nullable=false)
	private Orgperson orgperson;

	@Column(length=250)
	private String helpurlpath;

	@Column(length=1)
	private char presenterstatus;

	//bi-directional many-to-one association to Presenterappdetail	
	/*@OneToMany(mappedBy="presenterapp",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Presenterappdetail> presenterappdetails;*/

	@OneToMany(cascade=CascadeType.ALL) 
	@JoinColumn(name="presenterapp_presenterid")  
	private Set<Presenterappdetail> presenterappdetails;
	
	@Column(length=250)
	private String licenseapppath;
 	
	
	
	
	public Presenterapp() {
	}

	public int getPresenterid() {
		return this.presenterid;
	}

	public void setPresenterid(int presenterid) {
		this.presenterid = presenterid;
	}

	public String getApplicationname() {
		return this.applicationname;
	}

	public void setApplicationname(String applicationname) {
		this.applicationname = applicationname;
	}

	public String getApppath() {
		return this.apppath;
	}

	public void setApppath(String apppath) {
		this.apppath = apppath;
	}

 	public String getHelpurlpath() {
		return this.helpurlpath;
	}

	public void setHelpurlpath(String helpurlpath) {
		this.helpurlpath = helpurlpath;
	}

	public char getPresenterstatus() {
		return this.presenterstatus;
	}

	public void setPresenterstatus(char presenterstatus) {
		this.presenterstatus = presenterstatus;
	}

	public Set<Presenterappdetail> getPresenterappdetails() {
		return this.presenterappdetails;
	}

	public void setPresenterappdetails(Set<Presenterappdetail> presenterappdetails) {
		this.presenterappdetails = presenterappdetails;
	}

	public Presenterappdetail addPresenterappdetail(Presenterappdetail presenterappdetail) {
		getPresenterappdetails().add(presenterappdetail);
		presenterappdetail.setPresenterapp(this);

		return presenterappdetail;
	}

	public Presenterappdetail removePresenterappdetail(Presenterappdetail presenterappdetail) {
		getPresenterappdetails().remove(presenterappdetail);
		presenterappdetail.setPresenterapp(null);

		return presenterappdetail;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}
	
	public String getLicenseapppath() {
		return licenseapppath;
	}

	public void setLicenseapppath(String licenseapppath) {
		this.licenseapppath = licenseapppath;
	}

}