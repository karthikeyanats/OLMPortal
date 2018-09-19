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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the courselecture database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="courselecture")
public class Courselecture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int courselectureid;

	@Column(length=1)
	private String courselecturestatus;

	@Column(length=45)
	private String courselecturetitle;

	//bi-directional many-to-one association to Coursesection
	@ManyToOne
	@JoinColumn(name="coursesectionid", nullable=false)
	private Coursesection coursesection;

	//bi-directional many-to-one association to Lecturecontent
	@OneToMany(mappedBy="courselecture",fetch=FetchType.EAGER)
	private Set<Lecturecontent> lecturecontents;
	
	//bi-directional many-to-one association to Lecturecontent
	@OneToMany(mappedBy="courselecture",fetch=FetchType.EAGER)
	private Set<Metainfo> metainfo;
	
	//bi-directional many-to-one association to Lecturecontent
	@OneToMany(mappedBy="courselecture",fetch=FetchType.EAGER)
	private Set<Examquestion> examquestion;

	//bi-directional many-to-one association to Note
	@OneToMany(mappedBy="courselecture")
	private Set<Note> notes;
	
	@OneToMany(mappedBy="courselecture",fetch=FetchType.EAGER,cascade=CascadeType.ALL)  
	private Set<Faq> faq;
	
	@OneToMany(mappedBy="courselecture",fetch=FetchType.EAGER,cascade=CascadeType.ALL)  
	private Set<Quiz> quiz; 
	
	@OneToMany(mappedBy="courselecture",fetch=FetchType.EAGER,cascade=CascadeType.ALL)  
	private Set<Learningstatistic> learningstatistic; 

	@OneToMany(mappedBy="courselecture",fetch=FetchType.EAGER,cascade=CascadeType.ALL)  
	private Set<LectureReview> lecturereview; 
	
	@Column(length=45)
	private String seriallno; 
	
	public Courselecture() {
	}

	public int getCourselectureid() {
		return this.courselectureid;
	}

	public void setCourselectureid(int courselectureid) {
		this.courselectureid = courselectureid;
	}

	public String getCourselecturestatus() {
		return this.courselecturestatus;
	}

	public void setCourselecturestatus(String courselecturestatus) {
		this.courselecturestatus = courselecturestatus;
	}

	public String getCourselecturetitle() {
		return this.courselecturetitle;
	}

	public void setCourselecturetitle(String courselecturetitle) {
		this.courselecturetitle = courselecturetitle;
	}

	public Coursesection getCoursesection() {
		return this.coursesection;
	}

	public void setCoursesection(Coursesection coursesection) {
		this.coursesection = coursesection;
	}

	public Set<Lecturecontent> getLecturecontents() {
		return this.lecturecontents;
	}

	public void setLecturecontents(Set<Lecturecontent> lecturecontents) {
		this.lecturecontents = lecturecontents;
	}

	public Lecturecontent addLecturecontent(Lecturecontent lecturecontent) {
		getLecturecontents().add(lecturecontent);
		lecturecontent.setCourselecture(this);

		return lecturecontent;
	}

	public Lecturecontent removeLecturecontent(Lecturecontent lecturecontent) {
		getLecturecontents().remove(lecturecontent);
		lecturecontent.setCourselecture(null);

		return lecturecontent;
	}

	public Set<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	public Note addNote(Note note) {
		getNotes().add(note);
		note.setCourselecture(this);

		return note;
	}

	public Note removeNote(Note note) {
		getNotes().remove(note);
		note.setCourselecture(null);

		return note;
	}
	/*

	public String getCourselectureprogress() {
		return courselectureprogress;
	}

	public void setCourselectureprogress(String courselectureprogress) {
		this.courselectureprogress = courselectureprogress;
	}*/

	public Set<Faq> getFaq() {
		return faq;
	}

	public void setFaq(Set<Faq> faq) {
		this.faq = faq;
	}

	public Set<Quiz> getQuiz() {
		return quiz;
	}

	public void setQuiz(Set<Quiz> quiz) {
		this.quiz = quiz;
	}

	public Set<Learningstatistic> getLearningstatistic() {
		return learningstatistic;
	}

	public void setLearningstatistic(Set<Learningstatistic> learningstatistic) {
		this.learningstatistic = learningstatistic;
	}

	public String getSeriallno() {
		return seriallno;
	}

	public void setSeriallno(String seriallno) {
		this.seriallno = seriallno;
	}

	public Set<LectureReview> getLecturereview() {
		return lecturereview;
	}

	public void setLecturereview(Set<LectureReview> lecturereview) {
		this.lecturereview = lecturereview;
	}

}