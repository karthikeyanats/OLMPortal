package com.igrandee.product.ecloud.model.royalty;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.igrandee.product.ecloud.model.Orgperson;


/**
 * The persistent class for the paymentissue database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="paymentissue")
public class Paymentissue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="personid", nullable=false)
	private Orgperson createdby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofissue;

	@Column(length=1)
	private char paymentissuestatus;

	//bi-directional many-to-one association to Paymentrequest
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="paymentrequest_id", nullable=false)
	private Paymentrequest paymentrequest;

	public Paymentissue() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Orgperson getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Orgperson createdby) {
		this.createdby = createdby;
	}

	public Date getDateofissue() {
		return this.dateofissue;
	}

	public void setDateofissue(Date dateofissue) {
		this.dateofissue = dateofissue;
	}
	
	public Paymentrequest getPaymentrequest() {
		return this.paymentrequest;
	}

	public char getPaymentissuestatus() {
		return paymentissuestatus;
	}

	public void setPaymentissuestatus(char paymentissuestatus) {
		this.paymentissuestatus = paymentissuestatus;
	}

	public void setPaymentrequest(Paymentrequest paymentrequest) {
		this.paymentrequest = paymentrequest;
	}

}