package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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


/**
 * The persistent class for the message database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="message")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int messageid;

 	@ManyToOne
	@JoinColumn(name="fromorgpersonid", nullable=false)
	private Orgperson fromorgperson;
  
	@Column(length=500)
	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	private Date messagedate;

	@Column(length=1)
	private String messagestatus;

  	@ManyToOne
	@JoinColumn(name="toorgpersonid", nullable=false)
	private Orgperson toorgperson;

	public Message() {
	}

	public int getMessageid() {
		return this.messageid;
	}

	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}

 	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessagedate() {
		return this.messagedate;
	}

	public void setMessagedate(Date messagedate) {
		this.messagedate = messagedate;
	}

	public String getMessagestatus() {
		return this.messagestatus;
	}

	public void setMessagestatus(String messagestatus) {
		this.messagestatus = messagestatus;
	}

	public Orgperson getFromorgperson() {
		return fromorgperson;
	}

	public void setFromorgperson(Orgperson fromorgperson) {
		this.fromorgperson = fromorgperson;
	}

	public Orgperson getToorgperson() {
		return toorgperson;
	}

	public void setToorgperson(Orgperson toorgperson) {
		this.toorgperson = toorgperson;
	}
 
}