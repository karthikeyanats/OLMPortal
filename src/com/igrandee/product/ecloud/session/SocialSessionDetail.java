package com.igrandee.product.ecloud.session;

import org.springframework.context.annotation.Scope;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SocialSessionDetail {
	
	private Connection<Facebook> connection;
	
	private String socialmsg;	 

	private String courseid;
	
    private String courseenrollmentid;
    
    private String wishlisted;
    
    private String redirecturi;
    
    private String courseenrollmentstatus;
    
    private boolean fbcheck;
    
    
    public String getCourseenrollmentstatus() {
		return courseenrollmentstatus;
	}

	public void setCourseenrollmentstatus(String courseenrollmentstatus) {
		this.courseenrollmentstatus = courseenrollmentstatus;
	}	
	public boolean isFbcheck() {
		return fbcheck;
	}

	public void setFbcheck(boolean fbcheck) {
		this.fbcheck = fbcheck;
	}

	public String getRedirecturi() {
		return redirecturi;
	}

	public void setRedirecturi(String redirecturi) {
		this.redirecturi = redirecturi;
	}

	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}

	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}

	public String getWishlisted() {
		return wishlisted;
	}

	public void setWishlisted(String wishlisted) {
		this.wishlisted = wishlisted;
	}

	public String getSocialmsg() {
		return socialmsg;
	}

	public void setSocialmsg(String socialmsg) {
		this.socialmsg = socialmsg;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public Connection<Facebook> getConnection() {
		return connection;
	}

	public void setConnection(Connection<Facebook> connection) {
		this.connection = connection;
	}
	

}
