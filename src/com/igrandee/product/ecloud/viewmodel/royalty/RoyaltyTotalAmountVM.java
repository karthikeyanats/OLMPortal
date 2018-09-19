package com.igrandee.product.ecloud.viewmodel.royalty;

import org.springframework.stereotype.Component;

@Component
public class RoyaltyTotalAmountVM {
	
	String id;
	String authorid;
	String pendingamount;
	String royaltytotalamountstatus;
	String totalamount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthorid() {
		return authorid;
	}
	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	public String getPendingamount() {
		return pendingamount;
	}
	public void setPendingamount(String pendingamount) {
		this.pendingamount = pendingamount;
	}
	public String getRoyaltytotalamountstatus() {
		return royaltytotalamountstatus;
	}
	public void setRoyaltytotalamountstatus(String royaltytotalamountstatus) {
		this.royaltytotalamountstatus = royaltytotalamountstatus;
	}
	public String getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}

}
