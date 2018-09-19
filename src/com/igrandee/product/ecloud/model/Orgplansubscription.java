package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.igrandee.core.organization.model.Organization;


/**
 * The persistent class for the orgplansubscription database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name="orgplansubscription")
public class Orgplansubscription implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofsubscription;

	
	//private int orgid;
	@ManyToOne
	@JoinColumn(name="orgid", nullable=false)
	private Organization organization; 
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date planenddate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date planstartdate;

	//bi-directional many-to-one association to Plan
	@ManyToOne(optional=false)
	@JoinColumn(name="planid")
	private Plan plan;

	//bi-directional many-to-one association to Orgplanuser
	@OneToMany(mappedBy="orgplansubscription")
	private Set<Orgplanuser> orgplanusers;
	
	
	@Column(length=1)
	private String orgplanstatus;
	

	public Orgplansubscription() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateofsubscription() {
		return this.dateofsubscription;
	}

	public void setDateofsubscription(Date dateofsubscription) {
		this.dateofsubscription = dateofsubscription;
	}

	/*public int getOrgid() {
		return this.orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}*/
	
	

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Date getPlanenddate() {
		return this.planenddate;
	}

	public void setPlanenddate(Date planenddate) {
		this.planenddate = planenddate;
	}

	public Date getPlanstartdate() {
		return this.planstartdate;
	}

	public void setPlanstartdate(Date planstartdate) {
		this.planstartdate = planstartdate;
	}

	public Plan getPlan() {
		return this.plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public String getOrgplanstatus() {
		return orgplanstatus;
	}

	public void setOrgplanstatus(String orgplanstatus) {
		this.orgplanstatus = orgplanstatus;
	}
	
	public Set<Orgplanuser> getOrgplanusers() {
		return this.orgplanusers;
	}

	public void setOrgplanusers(Set<Orgplanuser> orgplanusers) {
		this.orgplanusers = orgplanusers;
	}

}