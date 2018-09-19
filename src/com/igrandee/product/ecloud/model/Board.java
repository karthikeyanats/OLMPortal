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
@Table(name="board")
public class Board implements Serializable {

	/**
	 * @author venkatraman_v
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int boardid;
	
	@Column(length=100)
	private String boardname;
	
	@Column(length=1)
	private char boardstatus;
	/*
	@OneToMany(mappedBy="board",fetch=FetchType.EAGER)
	private Set<Coursecategory> coursecategory;*/

	public int getBoardid() {
		return boardid;
	}

	public void setBoardid(int boardid) {
		this.boardid = boardid;
	}

	public String getBoardname() {
		return boardname;
	}

	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}

	public char getBoardstatus() {
		return boardstatus;
	}

	public void setBoardstatus(char boardstatus) {
		this.boardstatus = boardstatus;
	}

	/*public Set<Coursecategory> getCoursecategory() {
		return coursecategory;
	}

	public void setCoursecategory(Set<Coursecategory> coursecategory) {
		this.coursecategory = coursecategory;
	}*/
	
}
