package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the lecturecontent database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="lecturecontent")
public class Lecturecontent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int contentid;

	@Lob
	private byte[] content;

	@Column(length=200)
	private String contentpath;

	@Column(length=5)
	private String contentstatus;

	@Column(length=45)
	private String contenttype;
 
	@Column(length=100)
	private String referenceurl;
	
	@Column(length=45)
	private String duration;

	//bi-directional many-to-one association to Courselecture
	@ManyToOne
	@JoinColumn(name="courselectureid", nullable=false)
	private Courselecture courselecture;
	
	//bi-directional many-to-one association to Content
	@OneToMany(mappedBy="content",fetch=FetchType.EAGER)
	private Set<Contentapproval> contentapproval;

	public Lecturecontent() {
	}

	public int getContentid() {
		return this.contentid;
	}

	public void setContentid(int contentid) {
		this.contentid = contentid;
	}

 
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getContentpath() {
		return this.contentpath;
	}

	public void setContentpath(String contentpath) {
		this.contentpath = contentpath;
	}

	public String getContentstatus() {
		return this.contentstatus;
	}

	public void setContentstatus(String contentstatus) {
		this.contentstatus = contentstatus;
	}

	public String getContenttype() {
		return this.contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public String getReferenceurl() {
		return this.referenceurl;
	}

	public void setReferenceurl(String referenceurl) {
		this.referenceurl = referenceurl;
	}

	public Courselecture getCourselecture() {
		return this.courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}