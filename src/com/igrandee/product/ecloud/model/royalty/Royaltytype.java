package com.igrandee.product.ecloud.model.royalty;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the royaltytype database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="royaltytype")
public class Royaltytype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int royaltytypeid;

	private String royaltytype;

	private String royaltytypestatus;

	//bi-directional many-to-one association to Royaltyconfig
	@OneToMany(mappedBy="royaltytype")
	private List<Royaltyconfig> royaltyconfigs;

	public Royaltytype() {
	}

	public int getRoyaltytypeid() {
		return this.royaltytypeid;
	}

	public void setRoyaltytypeid(int royaltytypeid) {
		this.royaltytypeid = royaltytypeid;
	}

	public String getRoyaltytype() {
		return this.royaltytype;
	}

	public void setRoyaltytype(String royaltytype) {
		this.royaltytype = royaltytype;
	}

	public String getRoyaltytypestatus() {
		return this.royaltytypestatus;
	}

	public void setRoyaltytypestatus(String royaltytypestatus) {
		this.royaltytypestatus = royaltytypestatus;
	}

	public List<Royaltyconfig> getRoyaltyconfigs() {
		return this.royaltyconfigs;
	}

	public void setRoyaltyconfigs(List<Royaltyconfig> royaltyconfigs) {
		this.royaltyconfigs = royaltyconfigs;
	}

	public Royaltyconfig addRoyaltyconfig(Royaltyconfig royaltyconfig) {
		getRoyaltyconfigs().add(royaltyconfig);
		royaltyconfig.setRoyaltytype(this);

		return royaltyconfig;
	}

	public Royaltyconfig removeRoyaltyconfig(Royaltyconfig royaltyconfig) {
		getRoyaltyconfigs().remove(royaltyconfig);
		royaltyconfig.setRoyaltytype(null);

		return royaltyconfig;
	}

}