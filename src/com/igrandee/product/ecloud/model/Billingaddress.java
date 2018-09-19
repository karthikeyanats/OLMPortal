package com.igrandee.product.ecloud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the billingaddress database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="billingaddress")
public class Billingaddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int billingaddressid;

	@Column(length=45)
	private String billingaddress;

	@Column(length=45)
	private String billingaddresscity;

	@Column(length=45)
	private String billingaddresscountry;

	@Column(length=45)
	private String billingaddressstate;

	@Column(length=1)
	private String billingaddressstatus;

	@Column(length=45)
	private String billingaddresszipcode;

	//bi-directional many-to-one association to Paymentdetail
	@ManyToOne
	@JoinColumn(name="paymentdetailsid")
	private Paymentdetail paymentdetail1;

	//bi-directional many-to-one association to Paymentdetail
	@ManyToOne
	@JoinColumn(name="paymentdetails_paymentdetailsid", nullable=false)
	private Paymentdetail paymentdetail2;

	public Billingaddress() {
	}

	public int getBillingaddressid() {
		return this.billingaddressid;
	}

	public void setBillingaddressid(int billingaddressid) {
		this.billingaddressid = billingaddressid;
	}

	public String getBillingaddress() {
		return this.billingaddress;
	}

	public void setBillingaddress(String billingaddress) {
		this.billingaddress = billingaddress;
	}

	public String getBillingaddresscity() {
		return this.billingaddresscity;
	}

	public void setBillingaddresscity(String billingaddresscity) {
		this.billingaddresscity = billingaddresscity;
	}

	public String getBillingaddresscountry() {
		return this.billingaddresscountry;
	}

	public void setBillingaddresscountry(String billingaddresscountry) {
		this.billingaddresscountry = billingaddresscountry;
	}

	public String getBillingaddressstate() {
		return this.billingaddressstate;
	}

	public void setBillingaddressstate(String billingaddressstate) {
		this.billingaddressstate = billingaddressstate;
	}

	public String getBillingaddressstatus() {
		return this.billingaddressstatus;
	}

	public void setBillingaddressstatus(String billingaddressstatus) {
		this.billingaddressstatus = billingaddressstatus;
	}

	public String getBillingaddresszipcode() {
		return this.billingaddresszipcode;
	}

	public void setBillingaddresszipcode(String billingaddresszipcode) {
		this.billingaddresszipcode = billingaddresszipcode;
	}

	public Paymentdetail getPaymentdetail1() {
		return this.paymentdetail1;
	}

	public void setPaymentdetail1(Paymentdetail paymentdetail1) {
		this.paymentdetail1 = paymentdetail1;
	}

	public Paymentdetail getPaymentdetail2() {
		return this.paymentdetail2;
	}

	public void setPaymentdetail2(Paymentdetail paymentdetail2) {
		this.paymentdetail2 = paymentdetail2;
	}

}