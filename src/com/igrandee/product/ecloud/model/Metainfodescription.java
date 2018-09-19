package com.igrandee.product.ecloud.model;

import java.io.Serializable;

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
 * The persistent class for the metainfodescription database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name="metainfodescription")
public class Metainfodescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String description;

	private String descriptiontype;
	
	private String originalfilename;

	@ManyToOne
	@JoinColumn(name="metainfoid", nullable=false)
	private Metainfo metainfo;
	
	private String status;

	public Metainfodescription() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptiontype() {
		return this.descriptiontype;
	}

	public void setDescriptiontype(String descriptiontype) {
		this.descriptiontype = descriptiontype;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Metainfo getMetainfo() {
		return metainfo;
	}

	public void setMetainfo(Metainfo metainfo) {
		this.metainfo = metainfo;
	}

	public String getOriginalfilename() {
		return originalfilename;
	}

	public void setOriginalfilename(String originalfilename) {
		this.originalfilename = originalfilename;
	}

}