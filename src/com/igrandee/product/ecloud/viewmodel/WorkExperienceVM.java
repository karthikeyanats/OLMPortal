package com.igrandee.product.ecloud.viewmodel;

import org.springframework.stereotype.Component;

@Component
public class WorkExperienceVM {
  
	String organizationname;
	String designation;
	String aboutauthor;
	String awards;
	String websiteurl;
	String facebookurl;
	String twitterurl;
	String linkedinurl;
	
	
	
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}
	public String getOrganizationname() {
		return organizationname;
	}
	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAboutauthor() {
		return aboutauthor;
	}
	public void setAboutauthor(String aboutauthor) {
		this.aboutauthor = aboutauthor;
	}
	public String getWebsiteurl() {
		return websiteurl;
	}
	public void setWebsiteurl(String websiteurl) {
		this.websiteurl = websiteurl;
	}
	public String getFacebookurl() {
		return facebookurl;
	}
	public void setFacebookurl(String facebookurl) {
		this.facebookurl = facebookurl;
	}
	public String getTwitterurl() {
		return twitterurl;
	}
	public void setTwitterurl(String twitterurl) {
		this.twitterurl = twitterurl;
	}
	public String getLinkedinurl() {
		return linkedinurl;
	}
	public void setLinkedinurl(String linkedinurl) {
		this.linkedinurl = linkedinurl;
	}
}
