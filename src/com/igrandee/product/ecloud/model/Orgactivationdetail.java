package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the orgactivationdetails database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name="orgactivationdetails")
public class Orgactivationdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=1)
	private char activationstatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofactivation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofdeactivation;

	private int orgid;

	public Orgactivationdetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char getActivationstatus() {
		return this.activationstatus;
	}

	public void setActivationstatus(char activationstatus) {
		this.activationstatus = activationstatus;
	}

	public Date getDateofactivation() {
		return this.dateofactivation;
	}

	public void setDateofactivation(Date dateofactivation) {
		this.dateofactivation = dateofactivation;
	}

	public Date getDateofdeactivation() {
		return this.dateofdeactivation;
	}

	public void setDateofdeactivation(Date dateofdeactivation) {
		this.dateofdeactivation = dateofdeactivation;
	}

	public int getOrgid() {
		return this.orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

}