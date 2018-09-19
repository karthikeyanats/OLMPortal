package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the presenterapp_downloadhistory database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="presenterapp_downloadhistory")
public class PresenterappDownloadhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofdownload;

	@Column(length=1)
	private char historystatus;

	@Column(length=45)
	private String ipaddress;

 	@ManyToOne
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;
	

	//bi-directional many-to-one association to Presenterappdetail
	@ManyToOne
	@JoinColumn(name="presenterappdetails_id", nullable=false)
	private Presenterappdetail presenterappdetail;

	public PresenterappDownloadhistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateofdownload() {
		return this.dateofdownload;
	}

	public void setDateofdownload(Date dateofdownload) {
		this.dateofdownload = dateofdownload;
	}

	public char getHistorystatus() {
		return this.historystatus;
	}

	public void setHistorystatus(char historystatus) {
		this.historystatus = historystatus;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
 
	public Presenterappdetail getPresenterappdetail() {
		return this.presenterappdetail;
	}

	public void setPresenterappdetail(Presenterappdetail presenterappdetail) {
		this.presenterappdetail = presenterappdetail;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

}