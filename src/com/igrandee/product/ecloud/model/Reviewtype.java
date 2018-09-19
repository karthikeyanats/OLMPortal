package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
@Entity
@Table(name="reviewtype")
public class Reviewtype implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;
	
	@Column(length=45)
	private String reviewtype;
	
	@Column(length=50)
	private Timestamp dateofcreation;
	
	@Column(length=1)
	private char reviewtypestatus;
	
	//bi-directional many-to-one association to Paymentdetail
		@OneToMany(mappedBy="reviewtype",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
		private Set<LectureReview> lecturereview;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReviewtype() {
		return reviewtype;
	}

	public void setReviewtype(String reviewtype) {
		this.reviewtype = reviewtype;
	}

	public Timestamp getDateofcreation() {
		return dateofcreation;
	}

	public void setDateofcreation(Timestamp dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public char getReviewtypestatus() {
		return reviewtypestatus;
	}

	public void setReviewtypestatus(char reviewtypestatus) {
		this.reviewtypestatus = reviewtypestatus;
	}

	public Set<LectureReview> getLecturereview() {
		return lecturereview;
	}

	public void setLecturereview(Set<LectureReview> lecturereview) {
		this.lecturereview = lecturereview;
	}
	
	
}
