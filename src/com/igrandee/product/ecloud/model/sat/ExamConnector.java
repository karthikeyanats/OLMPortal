/**
 * 
 */
package com.igrandee.product.ecloud.model.sat;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.satutil.model.QuestionPaper;

/**
 * @author selvarajan_j
 *
 */
@Entity
@Table(name="examconnector")
public class ExamConnector implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="course_id", nullable=false)
	private Course course;
	
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name="questionpaper_id", nullable=false)
	private QuestionPaper questionpaper;
 	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	 
	/**
	 * @return the questionpaper
	 */
	public QuestionPaper getQuestionpaper() {
		return questionpaper;
	}


	/**
	 * @param questionpaper the questionpaper to set
	 */
	public void setQuestionpaper(QuestionPaper questionpaper) {
		this.questionpaper = questionpaper;
	}


	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}


	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
}
