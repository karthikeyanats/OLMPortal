package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the presentertype database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="presentertype")
public class Presentertype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateofcreation;

	@Column(length=45)
	private String typename;

	@Column(length=1)
	private char typestatus;

	//bi-directional many-to-one association to Presenterappdetail
	@OneToMany(mappedBy="presentertype")
	private Set<Presenterappdetail> presenterappdetails;

	public Presentertype() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateofcreation() {
		return this.dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public char getTypestatus() {
		return this.typestatus;
	}

	public void setTypestatus(char typestatus) {
		this.typestatus = typestatus;
	}

	public Set<Presenterappdetail> getPresenterappdetails() {
		return this.presenterappdetails;
	}

	public void setPresenterappdetails(Set<Presenterappdetail> presenterappdetails) {
		this.presenterappdetails = presenterappdetails;
	}

	public Presenterappdetail addPresenterappdetail(Presenterappdetail presenterappdetail) {
		getPresenterappdetails().add(presenterappdetail);
		presenterappdetail.setPresentertype(this);

		return presenterappdetail;
	}

	public Presenterappdetail removePresenterappdetail(Presenterappdetail presenterappdetail) {
		getPresenterappdetails().remove(presenterappdetail);
		presenterappdetail.setPresentertype(null);

		return presenterappdetail;
	}

}