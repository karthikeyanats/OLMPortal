package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the presenterappdetails database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="presenterappdetails")
public class Presenterappdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofcreation;

	@Column(length=1)
	private char detailstatus;

	private int logincount;

	private int workingduration;
	
	@Column()
	private String price;

	//bi-directional many-to-one association to PresenterappDownloadhistory
	@OneToMany(mappedBy="presenterappdetail")
	private Set<PresenterappDownloadhistory> presenterappDownloadhistories;

	//bi-directional many-to-one association to Presenterapp
	@ManyToOne
	@JoinColumn(name="presenterapp_presenterid", nullable=false)
	private Presenterapp presenterapp;

	//bi-directional many-to-one association to Presentertype
	@ManyToOne
	@JoinColumn(name="presentertype_id", nullable=false)
	private Presentertype presentertype;

	public Presenterappdetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public char getDetailstatus() {
		return this.detailstatus;
	}

	public void setDetailstatus(char detailstatus) {
		this.detailstatus = detailstatus;
	}

	public int getLogincount() {
		return this.logincount;
	}

	public void setLogincount(int logincount) {
		this.logincount = logincount;
	}

	public int getWorkingduration() {
		return this.workingduration;
	}

	public void setWorkingduration(int workingduration) {
		this.workingduration = workingduration;
	}

	public Set<PresenterappDownloadhistory> getPresenterappDownloadhistories() {
		return this.presenterappDownloadhistories;
	}

	public void setPresenterappDownloadhistories(Set<PresenterappDownloadhistory> presenterappDownloadhistories) {
		this.presenterappDownloadhistories = presenterappDownloadhistories;
	}

	public PresenterappDownloadhistory addPresenterappDownloadhistory(PresenterappDownloadhistory presenterappDownloadhistory) {
		getPresenterappDownloadhistories().add(presenterappDownloadhistory);
		presenterappDownloadhistory.setPresenterappdetail(this);

		return presenterappDownloadhistory;
	}

	public PresenterappDownloadhistory removePresenterappDownloadhistory(PresenterappDownloadhistory presenterappDownloadhistory) {
		getPresenterappDownloadhistories().remove(presenterappDownloadhistory);
		presenterappDownloadhistory.setPresenterappdetail(null);

		return presenterappDownloadhistory;
	}

	public Presenterapp getPresenterapp() {
		return this.presenterapp;
	}

	public void setPresenterapp(Presenterapp presenterapp) {
		this.presenterapp = presenterapp;
	}

	public Presentertype getPresentertype() {
		return this.presentertype;
	}

	public void setPresentertype(Presentertype presentertype) {
		this.presentertype = presentertype;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}