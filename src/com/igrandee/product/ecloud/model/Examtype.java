package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * The persistent class for the examtype database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="examtype")
public class Examtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int examtypeid;

	@Column(nullable=false, length=45)
	private String examtypename;

	@Column(nullable=false, length=1)
	private char examtypestatus;

	@ManyToOne 
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;
	
	@Column(nullable=false, length=45)
	private String typecreationdate;

	//bi-directional many-to-one association to Examquestion
	@OneToMany(mappedBy="examtype")
	private Set<Examquestion> examquestions;

	public Examtype() {
	}

	public int getExamtypeid() {
		return this.examtypeid;
	}

	public void setExamtypeid(int examtypeid) {
		this.examtypeid = examtypeid;
	}

	public String getExamtypename() {
		return this.examtypename;
	}

	public void setExamtypename(String examtypename) {
		this.examtypename = examtypename;
	}
 	 

 	public String getTypecreationdate() {
		return this.typecreationdate;
	}

	public void setTypecreationdate(String typecreationdate) {
		this.typecreationdate = typecreationdate;
	}

	public Set<Examquestion> getExamquestions() {
		return this.examquestions;
	}

	public void setExamquestions(Set<Examquestion> examquestions) {
		this.examquestions = examquestions;
	}

	public Examquestion addExamquestion(Examquestion examquestion) {
		getExamquestions().add(examquestion);
		examquestion.setExamtype(this);

		return examquestion;
	}

	public Examquestion removeExamquestion(Examquestion examquestion) {
		getExamquestions().remove(examquestion);
		examquestion.setExamtype(null);

		return examquestion;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public char getExamtypestatus() {
		return examtypestatus;
	}

	public void setExamtypestatus(char examtypestatus) {
		this.examtypestatus = examtypestatus;
	}

}