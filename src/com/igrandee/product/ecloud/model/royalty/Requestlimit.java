package com.igrandee.product.ecloud.model.royalty;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.igrandee.product.ecloud.model.Orgperson;


/**
 * The persistent class for the requestlimit database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="requestlimit")
public class Requestlimit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="personid", nullable=false)
	private Orgperson createdby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofcreation;

	private int minimumamount;

	@Column(length=1)
	private char requestlimitstatus;

	//bi-directional many-to-one association to Paymentrequest
	@OneToMany(mappedBy="requestlimit")
	private Set<Paymentrequest> paymentrequests;

	public Requestlimit() {
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

	public Date getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public int getMinimumamount() {
		return this.minimumamount;
	}

	public void setMinimumamount(int minimumamount) {
		this.minimumamount = minimumamount;
	}

	public char getRequestlimitstatus() {
		return requestlimitstatus;
	}

	public void setRequestlimitstatus(char requestlimitstatus) {
		this.requestlimitstatus = requestlimitstatus;
	}

	public Set<Paymentrequest> getPaymentrequests() {
		return this.paymentrequests;
	}

	public void setPaymentrequests(Set<Paymentrequest> paymentrequests) {
		this.paymentrequests = paymentrequests;
	}

	public Paymentrequest addPaymentrequest(Paymentrequest paymentrequest) {
		getPaymentrequests().add(paymentrequest);
		paymentrequest.setRequestlimit(this);

		return paymentrequest;
	}

	public Paymentrequest removePaymentrequest(Paymentrequest paymentrequest) {
		getPaymentrequests().remove(paymentrequest);
		paymentrequest.setRequestlimit(null);

		return paymentrequest;
	}

}