package com.igrandee.product.ecloud.model.livesession;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
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

import com.igrandee.product.ecloud.model.Livesession;
import com.igrandee.product.ecloud.model.Orgperson;


/**
 * The persistent class for the livesessionenrollment database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="livesessionenrollment")
public class Livesessionenrollment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int livesessionenrollmentid;

	@Column(length=1)
	private String status;

	//bi-directional many-to-one association to Livesession
	@ManyToOne
	@JoinColumn(name="livesessionid")
	private Livesession livesession;

	//bi-directional many-to-one association to Orgperson
	@ManyToOne
	@JoinColumn(name="orgpersonid")
	private Orgperson orgperson;

	//bi-directional many-to-one association to Livesessionpayment
	@OneToMany(mappedBy="livesessionenrollment",cascade=CascadeType.ALL)
	private Set<Livesessionpayment> livesessionpayments;

	//bi-directional many-to-one association to Livesessionrolyaltypayment
	@OneToMany(mappedBy="livesessionenrollment")
	private Set<Livesessionroyaltypayment> livesessionroyaltypayments;

	public Livesessionenrollment() {
	}

	public int getLivesessionenrollmentid() {
		return this.livesessionenrollmentid;
	}

	public void setLivesessionenrollmentid(int livesessionenrollmentid) {
		this.livesessionenrollmentid = livesessionenrollmentid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Livesession getLivesession() {
		return this.livesession;
	}

	public void setLivesession(Livesession livesession) {
		this.livesession = livesession;
	}

	public Orgperson getOrgperson() {
		return this.orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public Set<Livesessionpayment> getLivesessionpayments() {
		return this.livesessionpayments;
	}

	public void setLivesessionpayments(Set<Livesessionpayment> livesessionpayments) {
		this.livesessionpayments = livesessionpayments;
	}

	public Livesessionpayment addLivesessionpayment(Livesessionpayment livesessionpayment) {
		getLivesessionpayments().add(livesessionpayment);
		livesessionpayment.setLivesessionenrollment(this);

		return livesessionpayment;
	}

	public Livesessionpayment removeLivesessionpayment(Livesessionpayment livesessionpayment) {
		getLivesessionpayments().remove(livesessionpayment);
		livesessionpayment.setLivesessionenrollment(null);

		return livesessionpayment;
	}

	public Set<Livesessionroyaltypayment> getLivesessionroyaltypayments() {
		return this.livesessionroyaltypayments;
	}

	public void setLivesessionroyaltypayments(Set<Livesessionroyaltypayment> livesessionroyaltypayments) {
		this.livesessionroyaltypayments = livesessionroyaltypayments;
	}

	public Livesessionroyaltypayment addLivesessionroyaltypayment(Livesessionroyaltypayment livesessionroyaltypayment) {
		getLivesessionroyaltypayments().add(livesessionroyaltypayment);
		livesessionroyaltypayment.setLivesessionenrollment(this);

		return livesessionroyaltypayment;
	}

	public Livesessionroyaltypayment removeLivesessionrolyaltypayment(Livesessionroyaltypayment livesessionrolyaltypayment) {
		getLivesessionroyaltypayments().remove(livesessionrolyaltypayment);
		livesessionrolyaltypayment.setLivesessionenrollment(null);

		return livesessionrolyaltypayment;
	}

}