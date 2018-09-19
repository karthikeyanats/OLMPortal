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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the coursecategory database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="coursecategory")
public class Coursecategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int coursecategoryid;

	@Column(length=45)
	private String coursecategoryname;

	@Column
	private String coursecategorydescription;
	
	@Column(length=1)
	private String coursecategorystatus;
	
	@Column
	private String filename;

	@Column
	private String filepath;
	
	//bi-directional many-to-one association to Course
	@OneToMany(mappedBy="coursecategory",fetch=FetchType.EAGER)
	private Set<Course> courses;

	@ManyToOne
	@JoinColumn(name="boardid", nullable=false)
	private Board board;
	
	public Coursecategory() {
	}

	public int getCoursecategoryid() {
		return this.coursecategoryid;
	}

	public void setCoursecategoryid(int coursecategoryid) {
		this.coursecategoryid = coursecategoryid;
	}

	public String getCoursecategoryname() {
		return this.coursecategoryname;
	}

	public void setCoursecategoryname(String coursecategoryname) {
		this.coursecategoryname = coursecategoryname;
	}

	public String getCoursecategorystatus() {
		return this.coursecategorystatus;
	}

	public void setCoursecategorystatus(String coursecategorystatus) {
		this.coursecategorystatus = coursecategorystatus;
	}

	public Set<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Course addCours(Course cours) {
		getCourses().add(cours);
		cours.setCoursecategory(this);

		return cours;
	}

	public Course removeCours(Course cours) {
		getCourses().remove(cours);
		cours.setCoursecategory(null);

		return cours;
	}

	public String getCoursecategorydescription() {
		return coursecategorydescription;
	}

	public void setCoursecategorydescription(String coursecategorydescription) {
		this.coursecategorydescription = coursecategorydescription;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}