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
 * The persistent class for the coursesection database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="coursesection")
public class Coursesection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int coursesectionid;

	@Column(length=1)
	private String coursesectionstatus;

	@Column(length=45)
	private String coursesectiontitle;

	//bi-directional many-to-one association to Courselecture
	@OneToMany(mappedBy="coursesection",fetch=FetchType.EAGER)
	private Set<Courselecture> courselectures;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseid", nullable=false)
	private Course course;

	//bi-directional many-to-one association to Quiz
//	@OneToMany(mappedBy="coursesection")
//	private Set<Quiz> quizs;
	
	@Column(length=45)
	private String coursesectionprogress;
	
	
	public Coursesection() {
	}

	public int getCoursesectionid() {
		return this.coursesectionid;
	}

	public void setCoursesectionid(int coursesectionid) {
		this.coursesectionid = coursesectionid;
	}

	public String getCoursesectionstatus() {
		return this.coursesectionstatus;
	}

	public void setCoursesectionstatus(String coursesectionstatus) {
		this.coursesectionstatus = coursesectionstatus;
	}

	public String getCoursesectiontitle() {
		return this.coursesectiontitle;
	}

	public void setCoursesectiontitle(String coursesectiontitle) {
		this.coursesectiontitle = coursesectiontitle;
	}

	public Set<Courselecture> getCourselectures() {
		return this.courselectures;
	}

	public void setCourselectures(Set<Courselecture> courselectures) {
		this.courselectures = courselectures;
	}

	public Courselecture addCourselecture(Courselecture courselecture) {
		getCourselectures().add(courselecture);
		courselecture.setCoursesection(this);

		return courselecture;
	}

	public Courselecture removeCourselecture(Courselecture courselecture) {
		getCourselectures().remove(courselecture);
		courselecture.setCoursesection(null);

		return courselecture;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

//	public Set<Quiz> getQuizs() {
//		return this.quizs;
//	}
//
//	public void setQuizs(Set<Quiz> quizs) {
//		this.quizs = quizs;
//	}
 
	public String getCoursesectionprogress() {
		return coursesectionprogress;
	}

	public void setCoursesectionprogress(String coursesectionprogress) {
		this.coursesectionprogress = coursesectionprogress;
	}

}