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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the course database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="course")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int courseid;

	private Timestamp coursecreateddate;

	@Column(length=45)
	private String coursegoal;

	@Column(length=200)
	private String courselogo;

	@Column(length=200)
	private String coursepromevideopath;

	@Column(length=1)
	private String coursestatus;

	@Column(length=45)
	private String coursesubtitle;

	@Column(length=45)
	private String coursetitle;

	@Column(length=45)
	private String instructoinallevel;

	@Column(length=45)
	private String intendedaudience;

	@Column
	private String coursedescription;
	
	@Column
	private String courseprogress;
	

	//bi-directional many-to-one association to Coursecategory
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="coursecategoryid")
	private Coursecategory coursecategory;
	
	//bi-directional many-to-one association to Coursecategory
	@ManyToOne
	@JoinColumn(name="personid", nullable=false)
	private Orgperson orgperson;
	
	@ManyToOne
	@JoinColumn(name="mediumid", nullable=false)
	private Medium medium;
	
	@ManyToOne
	@JoinColumn(name="standardlevelid", nullable=false)
	private StandardLevel standardlevel;
	
	//bi-directional many-to-one association to Courseannouncement
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Courseannouncement> courseannouncements;

	/*//bi-directional many-to-one association to Coursecertificate
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Coursecertificate> coursecertificates;*/

	//bi-directional many-to-one association to Courseenrollment
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Courseenrollment> courseenrollments;

	//bi-directional many-to-one association to Courserating
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Courserating> courseratings;

	//bi-directional many-to-one association to Coursesection
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Coursesection> coursesections;

	//bi-directional many-to-one association to Faq
//	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
//	private Set<Faq> faqs;

	//bi-directional many-to-one association to Organizationcourse
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Organizationcourse> organizationcourses;

	//bi-directional many-to-one association to Price
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Price> prices;

	//bi-directional many-to-one association to Privacy
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Privacy> privacies;
	
	@OneToMany(mappedBy="course",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Orgplancourse> orgplancourses;


	public Course() {
	}

	public int getCourseid() {
		return this.courseid;
	}

	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	public Timestamp getCoursecreateddate() {
		return this.coursecreateddate;
	}

	public void setCoursecreateddate(Timestamp coursecreateddate) {
		this.coursecreateddate = coursecreateddate;
	}

	public String getCoursegoal() {
		return this.coursegoal;
	}

	public void setCoursegoal(String coursegoal) {
		this.coursegoal = coursegoal;
	}
 
	public String getCoursepromevideopath() {
		return this.coursepromevideopath;
	}

	public void setCoursepromevideopath(String coursepromevideopath) {
		this.coursepromevideopath = coursepromevideopath;
	}

	public String getCourselogo() {
		return courselogo;
	}

	public void setCourselogo(String courselogo) {
		this.courselogo = courselogo;
	}

	public String getCoursestatus() {
		return this.coursestatus;
	}

	public void setCoursestatus(String coursestatus) {
		this.coursestatus = coursestatus;
	}

	public String getCoursesubtitle() {
		return this.coursesubtitle;
	}

	public void setCoursesubtitle(String coursesubtitle) {
		this.coursesubtitle = coursesubtitle;
	}

	public String getCoursetitle() {
		return this.coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getInstructoinallevel() {
		return this.instructoinallevel;
	}

	public void setInstructoinallevel(String instructoinallevel) {
		this.instructoinallevel = instructoinallevel;
	}

	public String getIntendedaudience() {
		return this.intendedaudience;
	}

	public void setIntendedaudience(String intendedaudience) {
		this.intendedaudience = intendedaudience;
	}

	public Coursecategory getCoursecategory() {
		return this.coursecategory;
	}

	public void setCoursecategory(Coursecategory coursecategory) {
		this.coursecategory = coursecategory;
	}

	public Set<Courseannouncement> getCourseannouncements() {
		return this.courseannouncements;
	}

	public void setCourseannouncements(Set<Courseannouncement> courseannouncements) {
		this.courseannouncements = courseannouncements;
	}

	public Courseannouncement addCourseannouncement(Courseannouncement courseannouncement) {
		getCourseannouncements().add(courseannouncement);
		courseannouncement.setCourse(this);

		return courseannouncement;
	}

	public Courseannouncement removeCourseannouncement(Courseannouncement courseannouncement) {
		getCourseannouncements().remove(courseannouncement);
		courseannouncement.setCourse(null);

		return courseannouncement;
	}

	/*public Set<Coursecertificate> getCoursecertificates() {
		return this.coursecertificates;
	}

	public void setCoursecertificates(Set<Coursecertificate> coursecertificates) {
		this.coursecertificates = coursecertificates;
	}

	public Coursecertificate addCoursecertificate(Coursecertificate coursecertificate) {
		getCoursecertificates().add(coursecertificate);
		coursecertificate.setCourse(this);

		return coursecertificate;
	}

	public Coursecertificate removeCoursecertificate(Coursecertificate coursecertificate) {
		getCoursecertificates().remove(coursecertificate);
		coursecertificate.setCourse(null);

		return coursecertificate;
	}*/

	public Set<Courseenrollment> getCourseenrollments() {
		return this.courseenrollments;
	}

	public void setCourseenrollments(Set<Courseenrollment> courseenrollments) {
		this.courseenrollments = courseenrollments;
	}

	public Courseenrollment addCourseenrollment(Courseenrollment courseenrollment) {
		getCourseenrollments().add(courseenrollment);
		courseenrollment.setCourse(this);

		return courseenrollment;
	}

	public Courseenrollment removeCourseenrollment(Courseenrollment courseenrollment) {
		getCourseenrollments().remove(courseenrollment);
		courseenrollment.setCourse(null);

		return courseenrollment;
	}

	public Set<Courserating> getCourseratings() {
		return this.courseratings;
	}

	public void setCourseratings(Set<Courserating> courseratings) {
		this.courseratings = courseratings;
	}

	public Courserating addCourserating(Courserating courserating) {
		getCourseratings().add(courserating);
		courserating.setCourse(this);

		return courserating;
	}

	public Courserating removeCourserating(Courserating courserating) {
		getCourseratings().remove(courserating);
		courserating.setCourse(null);

		return courserating;
	}

	public Set<Coursesection> getCoursesections() {
		return this.coursesections;
	}

	public void setCoursesections(Set<Coursesection> coursesections) {
		this.coursesections = coursesections;
	}

	public Coursesection addCoursesection(Coursesection coursesection) {
		getCoursesections().add(coursesection);
		coursesection.setCourse(this);

		return coursesection;
	}

	public Coursesection removeCoursesection(Coursesection coursesection) {
		getCoursesections().remove(coursesection);
		coursesection.setCourse(null);

		return coursesection;
	}

//	public Set<Faq> getFaqs() {
//		return this.faqs;
//	}
//
//	public void setFaqs(Set<Faq> faqs) {
//		this.faqs = faqs;
//	}
//
//	public Faq addFaq(Faq faq) {
//		getFaqs().add(faq);
//		faq.setCourse(this);
//
//		return faq;
//	}
//
//	public Faq removeFaq(Faq faq) {
//		getFaqs().remove(faq);
//		faq.setCourse(null);
//
//		return faq;
//	}

	public Set<Organizationcourse> getOrganizationcourses() {
		return this.organizationcourses;
	}

	public void setOrganizationcourses(Set<Organizationcourse> organizationcourses) {
		this.organizationcourses = organizationcourses;
	}

	public Organizationcourse addOrganizationcours(Organizationcourse organizationcours) {
		getOrganizationcourses().add(organizationcours);
		organizationcours.setCourse(this);

		return organizationcours;
	}

	public Organizationcourse removeOrganizationcours(Organizationcourse organizationcours) {
		getOrganizationcourses().remove(organizationcours);
		organizationcours.setCourse(null);

		return organizationcours;
	}

	public Set<Price> getPrices() {
		return this.prices;
	}

	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}

	public Price addPrice(Price price) {
		getPrices().add(price);
		price.setCourse(this);

		return price;
	}

	public Price removePrice(Price price) {
		getPrices().remove(price);
		price.setCourse(null);

		return price;
	}

	public Set<Privacy> getPrivacies() {
		return this.privacies;
	}

	public void setPrivacies(Set<Privacy> privacies) {
		this.privacies = privacies;
	}

	public Privacy addPrivacy(Privacy privacy) {
		getPrivacies().add(privacy);
		privacy.setCourse(this);

		return privacy;
	}

	public Privacy removePrivacy(Privacy privacy) {
		getPrivacies().remove(privacy);
		privacy.setCourse(null);

		return privacy;
	}

	public String getCoursedescription() {
		return coursedescription;
	}

	public void setCoursedescription(String coursedescription) {
		this.coursedescription = coursedescription;
	}

	public String getCourseprogress() {
		return courseprogress;
	}

	public void setCourseprogress(String courseprogress) {
		this.courseprogress = courseprogress;
	}
	
	public Medium getMedium() {
		return medium;
	}

	public void setMedium(Medium medium) {
		this.medium = medium;
	}

	public StandardLevel getStandardlevel() {
		return standardlevel;
	}

	public void setStandardlevel(StandardLevel standardlevel) {
		this.standardlevel = standardlevel;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public Set<Orgplancourse> getOrgplancourses() {
		return orgplancourses;
	}

	public void setOrgplancourses(Set<Orgplancourse> orgplancourses) {
		this.orgplancourses = orgplancourses;
	}
	
	
}