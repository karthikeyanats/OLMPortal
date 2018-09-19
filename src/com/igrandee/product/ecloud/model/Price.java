package com.igrandee.product.ecloud.model;

import java.io.Serializable;
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
 * The persistent class for the price database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="price")
public class Price implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int priceid;

	@Column(length=45)
	private String price;

	@Column(length=1)
	private String pricestatus;

	//bi-directional many-to-one association to Invoice
	@OneToMany(mappedBy="price")
	private Set<Invoice> invoices;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="price")
	private Set<Payment> payments;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course course;

	public Price() {
	}

	public int getPriceid() {
		return this.priceid;
	}

	public void setPriceid(int priceid) {
		this.priceid = priceid;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPricestatus() {
		return this.pricestatus;
	}

	public void setPricestatus(String pricestatus) {
		this.pricestatus = pricestatus;
	}

	public Set<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Invoice addInvoice(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setPrice(this);

		return invoice;
	}

	public Invoice removeInvoice(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setPrice(null);

		return invoice;
	}

	public Set<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setPrice(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setPrice(null);

		return payment;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}