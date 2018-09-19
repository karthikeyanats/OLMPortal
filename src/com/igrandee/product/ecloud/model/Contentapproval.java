package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the contentapproval database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name="contentapproval")
public class Contentapproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int contentapprovalid;

	@ManyToOne
	@JoinColumn(name="approvedby")
	private Orgperson approvedby;
	
	@Column(length=1)
	private Character approvestatus;
	
	@Column(length=500)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="contentid")
	private Lecturecontent content;

	private Timestamp dateofapproval;

	public Contentapproval() {
	}

	public int getContentapprovalid() {
		return this.contentapprovalid;
	}

	public void setContentapprovalid(int contentapprovalid) {
		this.contentapprovalid = contentapprovalid;
	}

	public Timestamp getDateofapproval() {
		return this.dateofapproval;
	}

	public void setDateofapproval(Timestamp dateofapproval) {
		this.dateofapproval = dateofapproval;
	}

	public Orgperson getApprovedby() {
		return approvedby;
	}

	public void setApprovedby(Orgperson approvedby) {
		this.approvedby = approvedby;
	}

	public Lecturecontent getContent() {
		return content;
	}

	public void setContent(Lecturecontent content) {
		this.content = content;
	}

	public Character getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(Character approvestatus) {
		this.approvestatus = approvestatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}