package com.igrandee.product.ecloud.model;

import java.io.Serializable;
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
@Entity
@Table(name="medium")
public class Medium implements Serializable {

	/**
	 * @author venkatraman_v
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int mediumid;
	
	@Column(length=100)
	private String mediumname;
	
	@Column(length=1)
	private char mediumstatus;

	public int getMediumid() {
		return mediumid;
	}

	public void setMediumid(int mediumid) {
		this.mediumid = mediumid;
	}

	public String getMediumname() {
		return mediumname;
	}

	public void setMediumname(String mediumname) {
		this.mediumname = mediumname;
	}

	public char getMediumstatus() {
		return mediumstatus;
	}

	public void setMediumstatus(char mediumstatus) {
		this.mediumstatus = mediumstatus;
	}

	@OneToMany(mappedBy="medium",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Course> course;

	public Set<Course> getCourse() {
		return course;
	}

	public void setCourse(Set<Course> course) {
		this.course = course;
	}	
	
	
}
