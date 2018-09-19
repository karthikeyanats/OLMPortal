package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the paymentdetails database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="paymentdetails")
public class Paymentdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int paymentdetailsid;

	@Column(length=45)
	private String banklocation;

	@Column(length=45)
	private String bankname;

	@Column(length=45)
	private String cardnumber;

	private Timestamp chequedate;

	@Column(length=45)
	private String chequenumber;

	private Timestamp expirydate;

	//bi-directional many-to-one association to Billingaddress
	@OneToMany(mappedBy="paymentdetail1")
	private Set<Billingaddress> billingaddresses1;

	//bi-directional many-to-one association to Billingaddress
	@OneToMany(mappedBy="paymentdetail2")
	private Set<Billingaddress> billingaddresses2;

	//bi-directional many-to-one association to Payment
	@ManyToOne
	@JoinColumn(name="paymentid", nullable=false)
	private Payment payment;

	public Paymentdetail() {
	}

	public int getPaymentdetailsid() {
		return this.paymentdetailsid;
	}

	public void setPaymentdetailsid(int paymentdetailsid) {
		this.paymentdetailsid = paymentdetailsid;
	}

	public String getBanklocation() {
		return this.banklocation;
	}

	public void setBanklocation(String banklocation) {
		this.banklocation = banklocation;
	}

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getCardnumber() {
		return this.cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public Timestamp getChequedate() {
		return this.chequedate;
	}

	public void setChequedate(Timestamp chequedate) {
		this.chequedate = chequedate;
	}

	public String getChequenumber() {
		return this.chequenumber;
	}

	public void setChequenumber(String chequenumber) {
		this.chequenumber = chequenumber;
	}

	public Timestamp getExpirydate() {
		return this.expirydate;
	}

	public void setExpirydate(Timestamp expirydate) {
		this.expirydate = expirydate;
	}

	public Set<Billingaddress> getBillingaddresses1() {
		return this.billingaddresses1;
	}

	public void setBillingaddresses1(Set<Billingaddress> billingaddresses1) {
		this.billingaddresses1 = billingaddresses1;
	}

	public Billingaddress addBillingaddresses1(Billingaddress billingaddresses1) {
		getBillingaddresses1().add(billingaddresses1);
		billingaddresses1.setPaymentdetail1(this);

		return billingaddresses1;
	}

	public Billingaddress removeBillingaddresses1(Billingaddress billingaddresses1) {
		getBillingaddresses1().remove(billingaddresses1);
		billingaddresses1.setPaymentdetail1(null);

		return billingaddresses1;
	}

	public Set<Billingaddress> getBillingaddresses2() {
		return this.billingaddresses2;
	}

	public void setBillingaddresses2(Set<Billingaddress> billingaddresses2) {
		this.billingaddresses2 = billingaddresses2;
	}

	public Billingaddress addBillingaddresses2(Billingaddress billingaddresses2) {
		getBillingaddresses2().add(billingaddresses2);
		billingaddresses2.setPaymentdetail2(this);

		return billingaddresses2;
	}

	public Billingaddress removeBillingaddresses2(Billingaddress billingaddresses2) {
		getBillingaddresses2().remove(billingaddresses2);
		billingaddresses2.setPaymentdetail2(null);

		return billingaddresses2;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}