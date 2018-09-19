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


/**
 * The persistent class for the socialnetworktype database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="socialnetworktype")
public class Socialnetworktype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int socialnetworktypeid;

	@Column(nullable=false)
	private int personid;

	@Column(length=45)
	private String socialnetworktypename;

	@Column(length=45)
	private String socialnetworktypepassword;

	@Column(length=1)
	private String socialnetworktypestatus;

	@Column(length=100)
	private String socialnetworktypeurl;

	@Column(length=45)
	private String socialnetworktypeusername;

	public Socialnetworktype() {
	}

	public int getSocialnetworktypeid() {
		return this.socialnetworktypeid;
	}

	public void setSocialnetworktypeid(int socialnetworktypeid) {
		this.socialnetworktypeid = socialnetworktypeid;
	}

	public int getPersonid() {
		return this.personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

	public String getSocialnetworktypename() {
		return this.socialnetworktypename;
	}

	public void setSocialnetworktypename(String socialnetworktypename) {
		this.socialnetworktypename = socialnetworktypename;
	}

	public String getSocialnetworktypepassword() {
		return this.socialnetworktypepassword;
	}

	public void setSocialnetworktypepassword(String socialnetworktypepassword) {
		this.socialnetworktypepassword = socialnetworktypepassword;
	}

	public String getSocialnetworktypestatus() {
		return this.socialnetworktypestatus;
	}

	public void setSocialnetworktypestatus(String socialnetworktypestatus) {
		this.socialnetworktypestatus = socialnetworktypestatus;
	}

	public String getSocialnetworktypeurl() {
		return this.socialnetworktypeurl;
	}

	public void setSocialnetworktypeurl(String socialnetworktypeurl) {
		this.socialnetworktypeurl = socialnetworktypeurl;
	}

	public String getSocialnetworktypeusername() {
		return this.socialnetworktypeusername;
	}

	public void setSocialnetworktypeusername(String socialnetworktypeusername) {
		this.socialnetworktypeusername = socialnetworktypeusername;
	}

}