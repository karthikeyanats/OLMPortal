package com.igrandee.product.ecloud.model.livesession;

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
 * The persistent class for the livesessionpayment database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="livesessionpayment")
public class Livesessionpayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=45)
	private String orderno;

	@Column(length=45)
	private String paymenttype;

	@Column(length=1)
	private String status;
	
 	private Timestamp dateofpayment;


	//bi-directional many-to-one association to Livesessionenrollment
	@ManyToOne
	@JoinColumn(name="livesessionenrollmentid")
	private Livesessionenrollment livesessionenrollment;

	public Livesessionpayment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getPaymenttype() {
		return this.paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Livesessionenrollment getLivesessionenrollment() {
		return this.livesessionenrollment;
	}

	public void setLivesessionenrollment(Livesessionenrollment livesessionenrollment) {
		this.livesessionenrollment = livesessionenrollment;
	}

	public Timestamp getDateofpayment() {
		return dateofpayment;
	}

	public void setDateofpayment(Timestamp dateofpayment) {
		this.dateofpayment = dateofpayment;
	}

}