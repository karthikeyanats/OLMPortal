package com.igrandee.product.ecloud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
@Entity
@Table(name="standardlevel")
public class StandardLevel implements Serializable {

	/**
	 * @author venkatraman_v
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int standardlevelid;
	
	@Column(length=100)
	private String standardlevelname;
	
	@Column(length=1)
	private char standardlevelstatus;

	public int getStandardlevelid() {
		return standardlevelid;
	}

	public void setStandardlevelid(int standardlevelid) {
		this.standardlevelid = standardlevelid;
	}

	public String getStandardlevelname() {
		return standardlevelname;
	}

	public void setStandardlevelname(String standardlevelname) {
		this.standardlevelname = standardlevelname;
	}

	public char getStandardlevelstatus() {
		return standardlevelstatus;
	}

	public void setStandardlevelstatus(char standardlevelstatus) {
		this.standardlevelstatus = standardlevelstatus;
	}		
	
}