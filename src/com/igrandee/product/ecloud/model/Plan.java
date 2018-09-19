package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the plan database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="plan")
public class Plan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofcreation;

	private int duration;

	@Column(length=45)
	private String durationtype;
	@OneToMany(mappedBy="plan",fetch=FetchType.EAGER,cascade=CascadeType.ALL) 
	private Set<Orgplansubscription> orgplansubscriptions;
	
	private int maxcourse;

	private int maxusers;

	private int planamount;

	@Column(length=45)
	private String planname;

	@Column(length=1)
	private char planstatus;

	public Plan() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDurationtype() {
		return this.durationtype;
	}

	public void setDurationtype(String durationtype) {
		this.durationtype = durationtype;
	}

	public int getMaxcourse() {
		return this.maxcourse;
	}

	public void setMaxcourse(int maxcourse) {
		this.maxcourse = maxcourse;
	}

	public int getMaxusers() {
		return this.maxusers;
	}

	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}

	public int getPlanamount() {
		return this.planamount;
	}

	public void setPlanamount(int planamount) {
		this.planamount = planamount;
	}

	public String getPlanname() {
		return this.planname;
	}

	public void setPlanname(String planname) {
		this.planname = planname;
	}

	public char getPlanstatus() {
		return this.planstatus;
	}

	public void setPlanstatus(char planstatus) {
		this.planstatus = planstatus;
	}

	public Set<Orgplansubscription> getOrgplansubscriptions() {
		return orgplansubscriptions;
	}

	public void setOrgplansubscriptions(
			Set<Orgplansubscription> orgplansubscriptions) {
		this.orgplansubscriptions = orgplansubscriptions;
	}

	

}