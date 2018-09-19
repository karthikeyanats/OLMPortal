package com.igrandee.product.ecloud.model;

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


/**
 * The persistent class for the notificationreceived database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="notificationreceived")
public class Notificationreceived implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int notificationreceivedid;

 	@ManyToOne
	@JoinColumn(name="notificationid", nullable=false)
	private Notification notificationid;

 	@Column(length=45)
	private String reviewedstatus;

 	@ManyToOne
	@JoinColumn(name="toorgpersonid", nullable=false)
	private Orgperson orgperson;

	public Notificationreceived() {
	}

	public int getNotificationreceivedid() {
		return this.notificationreceivedid;
	}

	public void setNotificationreceivedid(int notificationreceivedid) {
		this.notificationreceivedid = notificationreceivedid;
	}
 
	public String getReviewedstatus() {
		return this.reviewedstatus;
	}

	public void setReviewedstatus(String reviewedstatus) {
		this.reviewedstatus = reviewedstatus;
	}

	public Notification getNotificationid() {
		return notificationid;
	}

	public void setNotificationid(Notification notificationid) {
		this.notificationid = notificationid;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}
 
}