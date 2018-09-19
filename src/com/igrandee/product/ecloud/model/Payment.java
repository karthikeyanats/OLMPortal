package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the payment database table.
 * 
 */
@Repository
@Entity
@Scope("prototype")
@Table(name="payment")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int paymentid;

	@Column(length=45)
	private String paymentamount;

	private Timestamp paymentdate;

	@Column(length=300)
	private String paymentdescription;

	@Column(length=1)
	private String paymentstatus;
	
	@Column(length=45)
	private String paymenttype;

	//bi-directional many-to-one association to Price
	@ManyToOne
	@JoinColumn(name="priceid", nullable=false)
	private Price price;

	//bi-directional many-to-one association to Paymentdetail
	@OneToMany(mappedBy="payment",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Paymentdetail> paymentdetails;

	@ManyToOne
	@JoinColumn(name="courseenrollmentid", nullable=false)
	private Courseenrollment courseenrollment; 

	//bi-directional many-to-one association to Giftcourse
	@ManyToOne
	@JoinColumn(name="giftcourseid", nullable=false)
	private GiftCourse giftcourse;
		
	@Column(length=100)
	private String orderno;
	
	public Payment() {
	}

	public int getPaymentid() {
		return this.paymentid;
	}

	public void setPaymentid(int paymentid) {
		this.paymentid = paymentid;
	}

	public String getPaymentamount() {
		return this.paymentamount;
	}

	public void setPaymentamount(String paymentamount) {
		this.paymentamount = paymentamount;
	}

	public Timestamp getPaymentdate() {
		return this.paymentdate;
	}

	public void setPaymentdate(Timestamp paymentdate) {
		this.paymentdate = paymentdate;
	}

	public String getPaymentdescription() {
		return this.paymentdescription;
	}

	public void setPaymentdescription(String paymentdescription) {
		this.paymentdescription = paymentdescription;
	}

	public String getPaymentstatus() {
		return this.paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getPaymenttype() {
		return this.paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Set<Paymentdetail> getPaymentdetails() {
		return this.paymentdetails;
	}

	public void setPaymentdetails(Set<Paymentdetail> paymentdetails) {
		this.paymentdetails = paymentdetails;
	}

	public Paymentdetail addPaymentdetail(Paymentdetail paymentdetail) {
		getPaymentdetails().add(paymentdetail);
		paymentdetail.setPayment(this);

		return paymentdetail;
	}

	public Paymentdetail removePaymentdetail(Paymentdetail paymentdetail) {
		getPaymentdetails().remove(paymentdetail);
		paymentdetail.setPayment(null);

		return paymentdetail;
	}

	public Courseenrollment getCourseenrollment() {
		return courseenrollment;
	}

	public void setCourseenrollment(Courseenrollment courseenrollment) {
		this.courseenrollment = courseenrollment;
	}

	public GiftCourse getGiftcourse() {
		return giftcourse;
	}

	public void setGiftcourse(GiftCourse giftcourse) {
		this.giftcourse = giftcourse;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
 
	
}