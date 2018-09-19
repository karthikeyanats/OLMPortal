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
 * The persistent class for the paymentrequest database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="paymentrequest")
public class Paymentrequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofrequest;

	@Column(length=1)
	private char paymentrequeststatus;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="personid", nullable=false)
	private Orgperson requestedby;

	//bi-directional many-to-one association to Paymentissue
	@OneToMany(mappedBy="paymentrequest")
	private Set<Paymentissue> paymentissues;

	//bi-directional many-to-one association to Requestlimit
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="requestlimit_id", nullable=false)
	private Requestlimit requestlimit;

	public Paymentrequest() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateofrequest() {
		return this.dateofrequest;
	}

	public void setDateofrequest(Date dateofrequest) {
		this.dateofrequest = dateofrequest;
	}

	public char getPaymentrequeststatus() {
		return paymentrequeststatus;
	}

	public void setPaymentrequeststatus(char paymentrequeststatus) {
		this.paymentrequeststatus = paymentrequeststatus;
	}



	public Orgperson getRequestedby() {
		return requestedby;
	}

	public void setRequestedby(Orgperson requestedby) {
		this.requestedby = requestedby;
	}

	public Set<Paymentissue> getPaymentissues() {
		return this.paymentissues;
	}

	public void setPaymentissues(Set<Paymentissue> paymentissues) {
		this.paymentissues = paymentissues;
	}

	public Paymentissue addPaymentissue(Paymentissue paymentissue) {
		getPaymentissues().add(paymentissue);
		paymentissue.setPaymentrequest(this);

		return paymentissue;
	}

	public Paymentissue removePaymentissue(Paymentissue paymentissue) {
		getPaymentissues().remove(paymentissue);
		paymentissue.setPaymentrequest(null);

		return paymentissue;
	}

	public Requestlimit getRequestlimit() {
		return this.requestlimit;
	}

	public void setRequestlimit(Requestlimit requestlimit) {
		this.requestlimit = requestlimit;
	}

}