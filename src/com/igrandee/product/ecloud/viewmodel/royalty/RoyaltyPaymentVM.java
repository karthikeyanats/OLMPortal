package com.igrandee.product.ecloud.viewmodel.royalty;

import org.springframework.stereotype.Component;

@Component
public class RoyaltyPaymentVM {

	String id;
	String authorroyaltyamount;
    String courseenrollmentid;	
	String dateofpyament;
	String orgroyaltyamount;
	String royaltypaymentstatus;
	String royaltyconfig;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthorroyaltyamount() {
		return authorroyaltyamount;
	}
	public void setAuthorroyaltyamount(String authorroyaltyamount) {
		this.authorroyaltyamount = authorroyaltyamount;
	}
	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}
	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}
	public String getDateofpyament() {
		return dateofpyament;
	}
	public void setDateofpyament(String dateofpyament) {
		this.dateofpyament = dateofpyament;
	}
	public String getOrgroyaltyamount() {
		return orgroyaltyamount;
	}
	public void setOrgroyaltyamount(String orgroyaltyamount) {
		this.orgroyaltyamount = orgroyaltyamount;
	}
	public String getRoyaltypaymentstatus() {
		return royaltypaymentstatus;
	}
	public void setRoyaltypaymentstatus(String royaltypaymentstatus) {
		this.royaltypaymentstatus = royaltypaymentstatus;
	}
	public String getRoyaltyconfig() {
		return royaltyconfig;
	}
	public void setRoyaltyconfig(String royaltyconfig) {
		this.royaltyconfig = royaltyconfig;
	}
	

}
