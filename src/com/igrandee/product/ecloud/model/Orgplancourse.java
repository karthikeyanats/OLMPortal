package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orgplancourse database table.
 * 
 */
@Entity
@Table(name="orgplancourse")
public class Orgplancourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	//bi-directional many-to-one association to Orgplansubscription
	@ManyToOne
	@JoinColumn(name="orgplansubscriptionid")
	private Orgplansubscription orgplansubscription;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseid")
	private Course course;

	public Orgplancourse() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Orgplansubscription getOrgplansubscription() {
		return this.orgplansubscription;
	}

	public void setOrgplansubscription(Orgplansubscription orgplansubscription) {
		this.orgplansubscription = orgplansubscription;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}