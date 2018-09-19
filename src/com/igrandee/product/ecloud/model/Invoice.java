package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the invoice database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="invoice")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int invoiceid;

	@Column(length=45)
	private String invoiceamount;

	private Timestamp invoicedate;

	@Column(length=200)
	private String invoicedescription;

	@Column(length=1)
	private String invoicestatus;

	//bi-directional many-to-one association to Price
	@ManyToOne
	@JoinColumn(name="priceid", nullable=false)
	private Price price;

	public Invoice() {
	}

	public int getInvoiceid() {
		return this.invoiceid;
	}

	public void setInvoiceid(int invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getInvoiceamount() {
		return this.invoiceamount;
	}

	public void setInvoiceamount(String invoiceamount) {
		this.invoiceamount = invoiceamount;
	}

	public Timestamp getInvoicedate() {
		return this.invoicedate;
	}

	public void setInvoicedate(Timestamp invoicedate) {
		this.invoicedate = invoicedate;
	}

	public String getInvoicedescription() {
		return this.invoicedescription;
	}

	public void setInvoicedescription(String invoicedescription) {
		this.invoicedescription = invoicedescription;
	}

	public String getInvoicestatus() {
		return this.invoicestatus;
	}

	public void setInvoicestatus(String invoicestatus) {
		this.invoicestatus = invoicestatus;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

}