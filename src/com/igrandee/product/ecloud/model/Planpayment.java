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
 * The persistent class for the planpayment database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="planpayment")
public class Planpayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int planpaymentid;

	@ManyToOne
	@JoinColumn(name="orgsubscriptionid") 
	private Orgplansubscription orgplansubscription;
	
	@Temporal(TemporalType.DATE)
	private Date planpaymentdate;

	@Column(length=1)
	private String planpaymentstatus;

	@Column(length=45)
	private String planpaymenttype;
	
	@Column(length=50)
	private String orderno;

	public Planpayment() {
	}

	public int getPlanpaymentid() {
		return this.planpaymentid;
	}

	public void setPlanpaymentid(int planpaymentid) {
		this.planpaymentid = planpaymentid;
	}

 	public Date getPlanpaymentdate() {
		return this.planpaymentdate;
	}

	public void setPlanpaymentdate(Date planpaymentdate) {
		this.planpaymentdate = planpaymentdate;
	}

	public String getPlanpaymentstatus() {
		return this.planpaymentstatus;
	}

	public void setPlanpaymentstatus(String planpaymentstatus) {
		this.planpaymentstatus = planpaymentstatus;
	}

	public String getPlanpaymenttype() {
		return this.planpaymenttype;
	}

	public void setPlanpaymenttype(String planpaymenttype) {
		this.planpaymenttype = planpaymenttype;
	}

	public Orgplansubscription getOrgplansubscription() {
		return orgplansubscription;
	}

	public void setOrgplansubscription(Orgplansubscription orgplansubscription) {
		this.orgplansubscription = orgplansubscription;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

}