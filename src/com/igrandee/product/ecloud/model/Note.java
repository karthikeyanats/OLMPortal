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
 * The persistent class for the notes database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="notes")
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int notesid;

	private Timestamp createddate;

	@Column(length=2000)
	private String notesdescription;
	
	private String notespath;

	//Set this as N for regular notes (typing inside textarea)
	//Set this as S for Scartch pad notes (Notes created by scratch pad )
	private String notetype;
	
	
	//Status R denoted private notes
	//Status U Denoted public notes
	//Status T for Trashed.
	@Column(length=1)
	private String notesstatus;

	//bi-directional many-to-one association to Courselecture
	@ManyToOne
	@JoinColumn(name="courselectureid", nullable=false)
	private Courselecture courselecture;

	@ManyToOne
	@JoinColumn(name="orgpersonid", nullable=false)
	private Orgperson orgperson;
	
	public Note() {
	}

	public int getNotesid() {
		return this.notesid;
	}

	public void setNotesid(int notesid) {
		this.notesid = notesid;
	}

	public Timestamp getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public String getNotesdescription() {
		return this.notesdescription;
	}

	public void setNotesdescription(String notesdescription) {
		this.notesdescription = notesdescription;
	}

	public String getNotesstatus() {
		return this.notesstatus;
	}

	public void setNotesstatus(String notesstatus) {
		this.notesstatus = notesstatus;
	}

	public Courselecture getCourselecture() {
		return this.courselecture;
	}

	public void setCourselecture(Courselecture courselecture) {
		this.courselecture = courselecture;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public String getNotespath() {
		return notespath;
	}

	public void setNotespath(String notespath) {
		this.notespath = notespath;
	}

	public String getNotetype() {
		return notetype;
	}

	public void setNotetype(String notetype) {
		this.notetype = notetype;
	}

}