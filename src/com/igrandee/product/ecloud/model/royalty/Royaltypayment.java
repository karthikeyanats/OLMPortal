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

import com.igrandee.product.ecloud.model.Courseenrollment;


/**
 * The persistent class for the royaltypayment database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="royaltypayment")
public class Royaltypayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	private float authorroyaltyamount;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseenrollmentid", nullable=false)
	private Courseenrollment courseenrollmentid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofpyament;

	private float orgroyaltyamount;

	@Column(length=1)
	private char royaltypaymentstatus;

	//bi-directional many-to-one association to Royaltyconfig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="royaltyconfig_id", nullable=false)
	private Royaltyconfig royaltyconfig;

	public Royaltypayment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAuthorroyaltyamount() {
		return this.authorroyaltyamount;
	}

	public void setAuthorroyaltyamount(float authorroyaltyamount) {
		this.authorroyaltyamount = authorroyaltyamount;
	}



	public Courseenrollment getCourseenrollmentid() {
		return courseenrollmentid;
	}

	public void setCourseenrollmentid(Courseenrollment courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}

	public Date getDateofpyament() {
		return this.dateofpyament;
	}

	public void setDateofpyament(Date dateofpyament) {
		this.dateofpyament = dateofpyament;
	}

	public float getOrgroyaltyamount() {
		return this.orgroyaltyamount;
	}

	public void setOrgroyaltyamount(float orgroyaltyamount) {
		this.orgroyaltyamount = orgroyaltyamount;
	}

	public char getRoyaltypaymentstatus() {
		return royaltypaymentstatus;
	}

	public void setRoyaltypaymentstatus(char royaltypaymentstatus) {
		this.royaltypaymentstatus = royaltypaymentstatus;
	}

	public Royaltyconfig getRoyaltyconfig() {
		return this.royaltyconfig;
	}

	public void setRoyaltyconfig(Royaltyconfig royaltyconfig) {
		this.royaltyconfig = royaltyconfig;
	}

}