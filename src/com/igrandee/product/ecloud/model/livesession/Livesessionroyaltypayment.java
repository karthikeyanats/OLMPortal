package com.igrandee.product.ecloud.model.livesession;

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

import com.igrandee.product.ecloud.model.royalty.Royaltyconfig;


/**
 * The persistent class for the livesessionrolyaltypayment database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="livesessionroyaltypayment")
public class Livesessionroyaltypayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	private float authorroylatyamount;

	private float orgroyaltyamount;

	@Column(length=1)
	private String status;

	//bi-directional many-to-one association to Livesessionenrollment
	@ManyToOne
	@JoinColumn(name="livesessionenrollmentid")
	private Livesessionenrollment livesessionenrollment;

	//bi-directional many-to-one association to Royaltyconfig
	@ManyToOne
	@JoinColumn(name="royaltyconfig_id")
	private Royaltyconfig royaltyconfig;

	public Livesessionroyaltypayment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public float getAuthorroylatyamount() {
		return authorroylatyamount;
	}

	public void setAuthorroylatyamount(float authorroylatyamount) {
		this.authorroylatyamount = authorroylatyamount;
	}

	public float getOrgroyaltyamount() {
		return orgroyaltyamount;
	}

	public void setOrgroyaltyamount(float orgroyaltyamount) {
		this.orgroyaltyamount = orgroyaltyamount;
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

	public Royaltyconfig getRoyaltyconfig() {
		return this.royaltyconfig;
	}

	public void setRoyaltyconfig(Royaltyconfig royaltyconfig) {
		this.royaltyconfig = royaltyconfig;
	}

}