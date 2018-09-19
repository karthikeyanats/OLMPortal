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
 * The persistent class for the royaltyconfig database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="royaltyconfig")
public class Royaltyconfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	private int authorroyalty;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="personid", nullable=false)
	private Orgperson createdby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofcreation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectfrom;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectto;

	private int orgroyalty;

	@Column(length=1)
	private char royaltyconfigstatus;
	
	//bi-directional many-to-one association to Royaltytype
	@ManyToOne
	@JoinColumn(name="royaltytypeid")
	private Royaltytype royaltytype;


	//bi-directional many-to-one association to Royaltypayment
	@OneToMany(mappedBy="royaltyconfig")
	private Set<Royaltypayment> royaltypayments;

	public Royaltyconfig() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthorroyalty() {
		return this.authorroyalty;
	}

	public void setAuthorroyalty(int authorroyalty) {
		this.authorroyalty = authorroyalty;
	}



	public Orgperson getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Orgperson createdby) {
		this.createdby = createdby;
	}

	public Date getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public Date getEffectfrom() {
		return this.effectfrom;
	}

	public void setEffectfrom(Date effectfrom) {
		this.effectfrom = effectfrom;
	}

	public Date getEffectto() {
		return this.effectto;
	}

	public void setEffectto(Date effectto) {
		this.effectto = effectto;
	}

	public int getOrgroyalty() {
		return this.orgroyalty;
	}

	public void setOrgroyalty(int orgroyalty) {
		this.orgroyalty = orgroyalty;
	}

	public char getRoyaltyconfigstatus() {
		return royaltyconfigstatus;
	}

	public void setRoyaltyconfigstatus(char royaltyconfigstatus) {
		this.royaltyconfigstatus = royaltyconfigstatus;
	}

	public Set<Royaltypayment> getRoyaltypayments() {
		return this.royaltypayments;
	}

	public void setRoyaltypayments(Set<Royaltypayment> royaltypayments) {
		this.royaltypayments = royaltypayments;
	}

	public Royaltypayment addRoyaltypayment(Royaltypayment royaltypayment) {
		getRoyaltypayments().add(royaltypayment);
		royaltypayment.setRoyaltyconfig(this);

		return royaltypayment;
	}

	public Royaltypayment removeRoyaltypayment(Royaltypayment royaltypayment) {
		getRoyaltypayments().remove(royaltypayment);
		royaltypayment.setRoyaltyconfig(null);

		return royaltypayment;
	}
	
	public Royaltytype getRoyaltytype() {
		return this.royaltytype;
	}

	public void setRoyaltytype(Royaltytype royaltytype) {
		this.royaltytype = royaltytype;
	}

}