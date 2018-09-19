package com.igrandee.product.ecloud.viewmodel;

import java.io.Serializable;


/**
 * @author seenivasagan_p
 * Content Approve View Model
 *
 */
public class ContentApproveVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/*variables for the view Object*/
	private String contentapprovalid;	
	private String approvedperson;	
	private String approvestatus;	
	private String description;	
	private String contentid;
	private String dateofapproval;
	private String authororgpersonid;
	private String courseid;
	private String coursestatus;
	private String coursetitle;
	
	/*getters and setters for the view Object*/
	public String getContentapprovalid() {
		return contentapprovalid;
	}

	public void setContentapprovalid(String contentapprovalid) {
		this.contentapprovalid = contentapprovalid;
	}

	public String getApprovedperson() {
		return approvedperson;
	}

	public void setApprovedperson(String approvedperson) {
		this.approvedperson = approvedperson;
	}

	public String getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(String approvestatus) {
		this.approvestatus = approvestatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public String getDateofapproval() {
		return dateofapproval;
	}

	public void setDateofapproval(String dateofapproval) {
		this.dateofapproval = dateofapproval;
	}

	public String getAuthororgpersonid() {
		return authororgpersonid;
	}

	public void setAuthororgpersonid(String authororgpersonid) {
		this.authororgpersonid = authororgpersonid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getCoursestatus() {
		return coursestatus;
	}

	public void setCoursestatus(String coursestatus) {
		this.coursestatus = coursestatus;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}
	
}