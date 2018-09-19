package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the notification database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="notification")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int notificationid;

 	@ManyToOne
	@JoinColumn(name="fromorgpersonid", nullable=false)
	private Orgperson orgperson;

	@Column(length=45)
	private String notificationcategory;

	private Timestamp notificationdate;

	@Column(length=500)
	private String notificationdescription;

	@Column(length=1)
	private String notificationstatus;

	
	@OneToMany(mappedBy="notificationid",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Notificationreceived> notificationreceived;
	
	public Notification() {
	}

	public int getNotificationid() {
		return this.notificationid;
	}

	public void setNotificationid(int notificationid) {
		this.notificationid = notificationid;
	}
  
	public String getNotificationcategory() {
		return this.notificationcategory;
	}

	public void setNotificationcategory(String notificationcategory) {
		this.notificationcategory = notificationcategory;
	}

	public Timestamp getNotificationdate() {
		return this.notificationdate;
	}

	public void setNotificationdate(Timestamp notificationdate) {
		this.notificationdate = notificationdate;
	}

	public String getNotificationdescription() {
		return this.notificationdescription;
	}

	public void setNotificationdescription(String notificationdescription) {
		this.notificationdescription = notificationdescription;
	}

	public String getNotificationstatus() {
		return this.notificationstatus;
	}

	public void setNotificationstatus(String notificationstatus) {
		this.notificationstatus = notificationstatus;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public Set<Notificationreceived> getNotificationreceived() {
		return notificationreceived;
	}

	public void setNotificationreceived(
			Set<Notificationreceived> notificationreceived) {
		this.notificationreceived = notificationreceived;
	}

}