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
 * The persistent class for the metainfo database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name="metainfo")
public class Metainfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int metainfoid;

	@Column(length=500)
	private String keywords;

 	@ManyToOne
	@JoinColumn(name="lectureid", nullable=false)
	private Courselecture courselecture;

	@Column(length=1)
	private String metainfostatus;

	@Column(length=500)
	private String reference;

	@Column(length=500)
	private String summary;

	@Column(length=500)
	private String weblinks;
	
 	@Column(length=500)
	private String diagrams;

	@Column(length=500)
	private String maps;

	@Column(length=500)
	private String tables;
	
	@Column(length=500)
	private String boxitem;
	
	@Column(length=500)
	private String projectideas;
	
	@Column(length=500)
	private String activity;
	

	public Metainfo() {
	}

	public int getMetainfoid() {
		return this.metainfoid;
	}

	public void setMetainfoid(int metainfoid) {
		this.metainfoid = metainfoid;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
 
	public Courselecture getCourselecture() {
		return courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}

	public String getMetainfostatus() {
		return this.metainfostatus;
	}

	public void setMetainfostatus(String metainfostatus) {
		this.metainfostatus = metainfostatus;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getWeblinks() {
		return this.weblinks;
	}

	public void setWeblinks(String weblinks) {
		this.weblinks = weblinks;
	}

	public String getDiagrams() {
		return diagrams;
	}

	public void setDiagrams(String diagrams) {
		this.diagrams = diagrams;
	}

	public String getMaps() {
		return maps;
	}

	public void setMaps(String maps) {
		this.maps = maps;
	}

	public String getTables() {
		return tables;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

	public String getBoxitem() {
		return boxitem;
	}

	public void setBoxitem(String boxitem) {
		this.boxitem = boxitem;
	}

	public String getProjectideas() {
		return projectideas;
	}

	public void setProjectideas(String projectideas) {
		this.projectideas = projectideas;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

}